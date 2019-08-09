import json
import os
import re
from abc import ABCMeta, abstractmethod

import jinja2
import networkx
from networkx import get_node_attributes

import archetype_extraction
from util import escape, copytree
from worddiff import get_htmls

any_but_not_space = re.compile(r"\S+")
_scriptdir = os.path.dirname(os.path.realpath(__file__))

environment = jinja2.Environment(loader=jinja2.FileSystemLoader(os.path.join(_scriptdir, 'graph_templates')))
page_name = "graph_group_"
extension = '.html'
short_cut_name_size = 50


def words(s: str) -> [str]:
    return re.findall(any_but_not_space, s)


def wrap_to_span(text, color=None):
    return '<span style="color:' + color + '\">' + text + '</span>' if color is not None \
        else '<span style="color: inherit">' + text + '</span>'


def not_empty(text):
    return not text.strip() == ''


def _get_archetype_tokens(text: [str]) -> [str]:
    return archetype_extraction.possible_n_tuples_lcs(tuple(tuple(words(sentence)) for sentence in text))


class RendererProcessor(metaclass=ABCMeta):

    @abstractmethod
    def render_pages(self):
        """
        Renders html pages for specified data
        :return:
        """
        pass


class GraphRendererProcessor(RendererProcessor):

    def __init__(self, data, src_text):
        self.src_text = src_text
        self.components = self.process_app_data(data, src_text)
        self.group_powers = [len(component) for component in self.components]
        self.group_archetypes = list(map(lambda comp: [
            comp.nodes[n]['archetype'] for n in comp.nodes][0], self.components))

    @abstractmethod
    def process_app_data(self, data, src_text):
        """
        Process application data for specific type of clones group
        :param data: json
        :param src_text:  plain text of source where clones were found
        :return: None
        """
        pass

    @abstractmethod
    def _html_node(self, node):
        """
        :returns: html representation for difference between archetype of group and current clone aka node
        """
        pass

    @abstractmethod
    def _html_edge(self, node):
        """
        :return: html representation for difference between current node one dege TODO change
        """
        pass

    @abstractmethod
    def _html_diff(self, u, v):
        """
        :returns: html difference between selected nodes
        """
        pass

    @abstractmethod
    def _get_archetype_for_main_page(self, archetype):
        """

        :param archetype:
        :return: get archetype html for displaying in nav bar
        """
        pass

    def render_pages(self) -> (str, [(str, int)]):
        """
        Renders main page and subpages for data as graph
        :return: Tuple where first element is main_page and [str] is subpages for components
        """
        return self._render_main_page(), self._render_subpages()

    def _render_main_page(self) -> str:
        group = [{'id': idx, 'archetype': self._get_archetype_for_main_page(archetype), 'power': power,
                  'data-groups': comp.data_groups, 'type': comp.type} for idx, (archetype, power, comp) in
                 enumerate(zip(self.group_archetypes, self.group_powers, self.components), start=1)]
        return environment.get_template('main_page_pattern.html').render(groups=group)

    def _render_subpages(self):
        pages = []
        for group_id, group in enumerate(self.components, start=1):
            nodes = [{  # Due to " will broke graph rendering
                'label': escape(node['name']).replace('\"', '&quot;'),
                'archetype': self._get_archetype_for_main_page(node['archetype']),
                'hl_range': node['range'],
                'id': n, 'html_diff': self._html_node(node)
            } for n, node in group.nodes(True)]
            edges = [{'from': u, 'to': v, 'html_from': self._html_edge(group.nodes[u]),
                      'html_to': self._html_edge(group.nodes[v]),
                      'html_diff': self._html_diff(group.nodes[u], group.nodes[v])} for (u, v) in group.edges]
            html = environment.get_template('component_page_pattern.html').render(nodes=nodes, edges=edges,
                                                                                  type=self.components[
                                                                                      group_id - 1].type,
                                                                                  data_groups=self.components[
                                                                                      group_id - 1].data_groups)
            pages.append((html, group_id))
        return pages


class GraphRendererProcessorFuzzyClones(GraphRendererProcessor):

    def _get_archetype_for_main_page(self, archetype):
        return escape(archetype)

    def process_app_data(self, data, src_text):
        data = data['groups']
        components = [networkx.DiGraph() for _ in data]
        glob_index = 0
        for graph, group in zip(components, data):
            archetype = ' '.join(_get_archetype_tokens(list(map(lambda dup: dup, group['archetype']))))

            [graph.add_node(index, name=src_text[dup['start_index']:dup['end_index'] + 1][:short_cut_name_size],
                            range=str(dup['start_index']) + '-' + str(dup['end_index']),
                            text=src_text[dup['start_index']:dup['end_index'] + 1], archetype=archetype) for index, dup
             in enumerate((group['duplicates']), start=glob_index + 1)]

            setattr(graph, 'data_groups', group['data-groups'])
            setattr(graph, 'type', 'single')

            glob_index += len(group['duplicates'])
            # should we set random edge?
        components = sorted(components, key=len, reverse=True)
        return components

    def _html_node(self, node):
        """
        :param node:
        :return:
        """
        archetype = node['archetype']
        clone = node['text']
        return get_htmls([clone, archetype])[1]
        # return get_htmls([archetype, clone])[1]

    def _html_edge(self, node):
        return self._html_node(node)

    def _html_diff(self, u, v):
        u_text = u['text']
        v_text = v['text']
        return get_htmls([u_text, v_text])[1]


class GraphRendererProcessorCommentsClones(GraphRendererProcessor):
    """
        Construct pages for comments clones see - digraph3.json file
    """

    def _get_archetype_for_main_page(self, archetype):
        return escape(archetype)

    def process_app_data(self, data, src_text):
        """
         Example of data
         {
       "digraph": [
         {
           "vertexName": "Method public SortedDocValues select(final SortedSetDocValues values, final BitSet parentDocs, final DocIdSetIterator childDocs, int maxChildren) throws IOException  (MultiValueMode.java)",
           "children": [
             {
               "name": "Method public BinaryDocValues select(final SortedBinaryDocValues values, final BytesRef missingValue)  (MultiValueMode.java)"
             }
           ],
           "comment": "\n     * Return a {@link SortedDocValues} instance that can be used to sort root documents\n     * with this mode, the provided values and filters for root/inner documents.\n     *\n     * For every root document, the values of its inner documents will be aggregated.\n     *\n     * Allowed Modes: MIN, MAX\n     *\n     * NOTE: Calling the returned instance on docs that are not root docs is illegal\n     *       The returned instance can only be evaluate the current and upcoming docs\n     ",
           "vertexLabel": "SortedDocValues select()"
         },
         {
           "vertexName": "Method public String[] ignoreIndexSettings()  (RestoreSnapshotRequest.java)",
           "children": [
             {
               "name": "Method public RestoreSnapshotRequest ignoreIndexSettings(List<String> ignoreIndexSettings)  (RestoreSnapshotRequest.java)"
             }
           ],
           "comment": "\n     * Returns the list of index settings and index settings groups that shouldn't be restored from snapshot\n     ",
           "vertexLabel": "String[] ignoreIndexSettings()"
         },
         ...
         """
        data = data['digraph']
        directed_graph = networkx.DiGraph()
        for index, vtx in enumerate(data, start=1):
            directed_graph.add_node(index, name=vtx['vertexName'], text=vtx['comment'],
                                    range='1-2'  # since no range is given
                                    )

        for i, source in enumerate(data, start=1):
            if 'children' in source:
                for destination in source['children']:
                    dest_name = destination['name']
                    indexes_dest = [j for j, data in directed_graph.nodes(True) if data['name'] == dest_name]
                    for to in indexes_dest:
                        directed_graph.add_edge(i, to)

        components = [directed_graph.subgraph(c) for c in
                      sorted(networkx.connected_components(networkx.Graph(directed_graph)),
                             key=len, reverse=True)]

        for connected_component in components:
            component_text = [connected_component.nodes[index]['text'] for index in
                              connected_component.nodes]
            archetype = ' '.join(_get_archetype_tokens(component_text))
            # Warning from docs The call order of arguments values and name switched between v1.x & v2.x.
            networkx.set_node_attributes(connected_component, archetype, 'archetype')

        def _build_data_groups(components):
            # TODO discuss about 0 infileino
            return list(
                map(lambda component: ','.join(
                    map(lambda x: '0:' + x, get_node_attributes(component, 'range').values())),
                    components))

        for (component, data_group) in zip(components, _build_data_groups(components)):
            setattr(component, 'type', 'single')
            setattr(component, 'data_groups', data_group)
        return components

    def _html_node(self, node):
        """
        :param node:
        :return:
        """
        archetype = node['archetype']
        clone = node['text']
        return get_htmls([clone, archetype])[1]

    def _html_edge(self, node):
        return self._html_node(node)

    def _html_diff(self, u, v):
        u_text = u['text']
        v_text = v['text']
        return get_htmls([u_text, v_text])[1]


class GraphRendererProcessorExactClones(GraphRendererProcessor):

    def _get_archetype_for_main_page(self, group_archetype):
        # variations = archetype['variations']
        return '<span class="variations_part" style="color:red">&#60;Ext.p&#62;</span>'.join(
            map(lambda clone: "<span class='exact_clone_part'>" + escape(clone) + "</span>", group_archetype))

    def variations(self, variations):
        if not variations:
            # means no extensions points
            return []
        return list(map(list, zip(*variations)))

    def process_app_data(self, data, src_text):
        data = data['groups']
        components = [networkx.DiGraph() for _ in data]
        glob_index = 0
        for graph, group in zip(components, data):
            archetype = group['archetype']['archetypes']
            tmp = group['archetype']['variations']
            variations = list(map(list, zip(*tmp))) if tmp else [[] for _ in group['duplicates']]

            [graph.add_node(
                index, name=src_text[dup['start_index']:dup['end_index'] + 1][:short_cut_name_size],
                range=str(dup['start_index']) + '-' + str(dup['end_index']),
                text=src_text[dup['start_index']:dup['end_index'] + 1], archetype=archetype,
                variations=variation) for index, (dup, variation)
                in enumerate(zip(group['duplicates'], variations), start=glob_index + 1)]
            setattr(graph, 'data_groups', group['data-groups'])
            setattr(graph, 'archetypes', group['archetype']['archetypes'])
            setattr(graph, 'type', 'multiple') if tmp else setattr(graph, 'type', 'single')
            glob_index += len(group['duplicates'])
            # TODO should we set random edge? or user can on select two nodes see diff
        components = sorted(components, key=len, reverse=True)
        return components

    def _html_node(self, node):
        archetypes = list(map(lambda x: wrap_to_span(escape(x)), node['archetype']))
        variations = list(map(escape, node['variations']))
        res = archetypes[0]
        for n in range(len(archetypes) - 1):
            variation = wrap_to_span(variations[n], 'red') if not_empty(variations[n]) else wrap_to_span("&#949;",
                                                                                                         'red')  # epsilon
            res += variation + archetypes[n + 1]
        return res

    def _html_edge(self, node):
        return self._html_node(node)

    def _html_diff(self, u, v):
        wrap_red = lambda x: wrap_to_span(x, 'red')
        eps = "&#949;"
        wrap_yellow = lambda x: wrap_to_span(x, 'yellow')

        archetypes = list(map(lambda x: wrap_to_span(escape(x)), u['archetype']))
        var_u = list(map(escape, u['variations']))
        var_v = list(map(escape, v['variations']))
        res = archetypes[0]
        for n in range(len(archetypes) - 1):
            wrapped_u = wrap_red(var_u[n]) if not_empty(var_u[n]) else wrap_red(eps)
            wrapped_v = wrap_yellow(var_v[n]) if not_empty(var_v[n]) else wrap_yellow(eps)
            variation = wrapped_u + wrapped_v
            res += variation + archetypes[n + 1]
        return res


def html_report_graph_auto_mode(report_dir, src_text) -> str:
    """
    Renders pages for graph mode and returns url for main_page
    :param report_dir: where will be saved
    :param src_text: source text where clones were found
    :return: path to file
    """
    from external_tool_unifier import unified_name_for_output, exact_clone_strategy
    print("Start making report for graph mode...")
    tool_output = unified_name_for_output
    with open(os.path.join(report_dir, tool_output), 'r') as output_file:
        data = json.load(output_file)
        dst_folder = os.path.join(report_dir, 'graph_mode')
        graph_renderer = GraphRendererProcessorExactClones(data, src_text) if data['type'] == exact_clone_strategy \
            else GraphRendererProcessorFuzzyClones(data, src_text)
        main_page, subpages = graph_renderer.render_pages()
        copytree(os.path.join(_scriptdir, 'graph_templates'),
                 dst_folder, ['component_page_pattern.html', 'main_page_pattern.html'])
        url_main_page = os.path.join(dst_folder, 'graph_mode_main_page.html')
        with open(url_main_page, 'w') as file:
            file.write(main_page)
        for (html, idx) in subpages:
            url_page = os.path.join(dst_folder, page_name + str(idx) + extension)
            with open(url_page, 'w') as file:
                file.write(html)

        print("Finished reporting for graph mode...")
        print('Files saved to' + dst_folder)
        return url_main_page


def render_test_data():
    test_folder = 'test'
    test_file = 'digraph-4.json'
    print("Start making report for graph mode...")
    with open(os.path.join(test_folder, test_file), 'r') as output_file:
        data = json.load(output_file)
        graph_renderer = GraphRendererProcessorCommentsClones(data, '')
        main_page, subpages = graph_renderer.render_pages()
        dst_folder = os.path.join(test_folder, 'testing')
        copytree(os.path.join(_scriptdir, 'graph_templates'),
                 dst_folder, ['component_page_pattern.html', 'main_page_pattern.html'])

        url_main_page = os.path.join(dst_folder, 'graph_mode_main_page.html')
        with open(url_main_page, 'w') as file:
            file.write(main_page)

        for (html, idx) in subpages:
            url_page = os.path.join(dst_folder, page_name + str(idx) + extension)
            with open(url_page, 'w') as file:
                file.write(html)
        print("Finished reporting for graph mode...")
        return url_main_page

# uncomment for see result for testing data
#print(render_test_data())
