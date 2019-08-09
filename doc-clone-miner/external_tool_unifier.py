import json
import os

_scriptdir = os.path.dirname(os.path.realpath(__file__))
_output_tools_dir = os.path.join(_scriptdir, 'external_tools_output')

unified_name_for_output = 'tool_result.json'

fuzzy_strategy = 'fuzzy'
exact_clone_strategy = 'exact'

"""
Json structure for ExactClones:
 {
 groups:[
     {
      "group_id": 33,
      "duplicates": [
        {
          "start_index": 258641,
          "end_index": 258940
        },
        {
          "start_index": 259064,
          "end_index": 259353
        }
      ],
      "data-groups": "0:258641-258833,0:259064-259256;0:258854-258940,0:259267-259353",
      "archetype": {
        "archetypes": [
          "| 2002-11-26  Norman Walsh <nwalsh@users.sourcefor",
          "| 2002-12-28  Norman Walsh <nwalsh@useentry.xml: "
        ],
        "variations": [
          [
            "simplemsgentry/*\n\n  ",
            "xref/*\n\n  "
          ]
        ]
      }
    }, ...]
 type: "exact"
}
"""

"""
Json structure for FuzzyClones:
{
  "groups": [
    {
      "group_id": 1,
      "duplicates": [
        {
          "start_index": 342,
          "end_index": 14859
        },
        {
          "start_index": 14861,
          "end_index": 30320
        }
      ],
      "data-groups": "0:342-14859,0:14861-30320",
      "archetype": [
        "This",
        "This"
      ]
    },...
   ]
   "type": "fuzzy"
"""


def _get_archetype_fuzzy(variative_elem) -> [str]:
    """
    Extracts instancetexts  from FuzzyClone group  (each of them could be treats as reference_text
    :param variative_elem: VariativeElement that contains Fuzzy clone group
    :return: instance texts
    """
    # TODO is it always one element?
    if len(variative_elem.clone_groups) >= 2:
        raise Exception("Incorrect size of clone group")
    return variative_elem.clone_groups[0].instancetexts


def _get_archetype_with_variation_part(clone_group):
    """
    Extracts archetype parts and variations part for ExactGroup
    :param clone_group: ExactGroup
    :return: {arhectype:[str],variations:[[str]]}
    """
    nextpoints = len(clone_group.clone_groups) - 1
    variations = [clone_group.getvariations(i) for i in
                  range(nextpoints)]  # c [V1,V2,V3...]1 c [V1,V2,V3...]2 c ..[..] C
    # CO, C1...CN
    # TODO what is better?
    # archetypes = [exact_clone.text() for exact_clone in clone_group.clone_groups]
    archetypes = [exact_clone.plain_text() for exact_clone in clone_group.clone_groups]

    return {'archetypes': archetypes, 'variations': variations}


def save_clones_as_json(working_dir, clones, is_exact_clones=True):
    """
    Convert clones to unified json format in working directory with @unified_name_for_output
     for further working with them from other components
    :param is_exact_clones: is instances of ExactClones or FuzzyClones
    :param working_dir: place where current working instance aka where pyvarelements
    :param clones:
    :return: None
    """
    data = get_structured_data(clones, is_exact_clones)
    file_name_out = os.path.join(working_dir, unified_name_for_output)
    with open(file_name_out, 'w') as file_out:
        json.dump(data, file_out)
        print('Clones saved to: ' + file_name_out)


def get_structured_data(clones, is_exact_clones=True):
    data = {}
    group_id = 0
    groups = []
    for clone in clones:
        startgrp = clone.clone_groups[0]
        starts = [s for (fno, s, e) in startgrp.instances]
        endgrp = clone.clone_groups[-1]
        ends = [e for (fno, s, e) in endgrp.instances]
        group_id += 1
        duplicates = [{
            'start_index': int(beg),
            'end_index': int(end)} for (beg, end) in
            zip(starts, ends)]
        groups.append({'group_id': group_id, 'duplicates': duplicates, 'data-groups': clone.textdescriptor,
                       'archetype': _get_archetype_with_variation_part(clone) if is_exact_clones else
                       _get_archetype_fuzzy(clone)})
    data['groups'] = groups
    data['type'] = exact_clone_strategy if is_exact_clones else fuzzy_strategy
    return data


if __name__ == '__main__':
    raise Exception("This is not an entry point")
