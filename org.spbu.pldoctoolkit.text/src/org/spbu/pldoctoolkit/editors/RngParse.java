package org.spbu.pldoctoolkit.editors;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RngParse {

	private Hashtable<String, Vertex> defines;
	private Hashtable<String, Vertex> elements;
	private Hashtable<Vertex, Boolean> empty;
	private HashSet<Vertex> notAllowed;
	private Vertex start;

	class SaxParserSchem extends DefaultHandler {
		Stack<Vertex> openedTags;
		Vertex st;

		public void startDocument() {
			openedTags = new Stack<Vertex>();
		}

		public void startElement(String uri, String localName, String qname,
				Attributes attr) {
			Vertex buf = null;
			if (qname == "start") {
				buf = new Vertex(0, null);
				st = buf;
			}
			if (qname == "define") {
				buf = new Vertex(1, null, attr.getValue(0));
				defines.put(attr.getValue(0), buf);
			}
			if (qname == "element") {

				if (attr.getLength() != 0) {
					buf = new Vertex(2, openedTags.lastElement(), attr
							.getValue(0));
					elements.put(attr.getValue(0), buf);
				} else {
					buf = new Vertex(2, openedTags.lastElement());

				}
			}
			if (qname == "group")
				buf = new Vertex(3, openedTags.lastElement());

			if (qname == "attribute") {
				if (attr.getLength() != 0)
					buf = new Vertex(4, openedTags.lastElement(), attr
							.getValue(0));
				else
					buf = new Vertex(4, openedTags.lastElement());
			}
			if (qname == "choice")
				buf = new Vertex(5, openedTags.lastElement());

			if (qname == "zeroOrMore")
				buf = new Vertex(6, openedTags.lastElement());

			if (qname == "oneOrMore")
				buf = new Vertex(7, openedTags.lastElement());

			if (qname == "optional")
				buf = new Vertex(8, openedTags.lastElement());

			if (qname == "ref") {
				buf = new Vertex(9, openedTags.lastElement(), attr.getValue(0));
			}

			if (qname == "notAllowed") {
				buf = new Vertex(10, openedTags.lastElement());
			}

			if (qname == "value") {
			}
			if (buf != null) {
				if (!openedTags.isEmpty())
					openedTags.lastElement().addChild(buf);
				openedTags.push(buf);
			}

		}

		public void endElement(String uri, String localName, String qname) {
			if (!((qname == "start") || (qname == "choice")
					|| (qname == "attribute") || (qname == "oneOrMore")
					|| (qname == "zeroOrMore") || (qname == "optional")
					|| (qname == "group") || (qname == "ref")
					|| (qname == "define") || (qname == "notAllowed") || (qname == "element"))) {
				return;
			}
			openedTags.pop();

		}

		public void characters(char[] ch, int start, int length) {
		}

		public void ignorableWhitespace(char[] ch, int start, int length) {
		}

		public void endDocument() {
			start = st;
		}

	}

	public RngParse(URL[] shm) {
		empty = new Hashtable<Vertex, Boolean>();
		defines = new Hashtable<String, Vertex>();
		elements = new Hashtable<String, Vertex>();
		notAllowed = new HashSet<Vertex>();
		SAXParserFactory spf = SAXParserFactory.newInstance();

		SAXParser parser;
		try {
			parser = spf.newSAXParser();

			for (int i = 0; i < shm.length; i++) {
				parser.parse(new InputSource(shm[i].toString()),
						new SaxParserSchem());

			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		for (Iterator iterator = defines.values().iterator(); iterator
				.hasNext();) {
			Vertex vertex = (Vertex) iterator.next();
			if ((!vertex.child.isEmpty())
					&& (vertex.child.getFirst().type == 10)) {
				notAllowed.add(vertex);
			}
			if (!empty.containsKey(vertex))
				elementProof(vertex);
		}
	}

	// return all element, which were defined in schem
	public HashSet<String> getElements() {
		HashSet<String> result = new HashSet<String>();
		for (String s : elements.keySet()) {
			result.add(s);
		}
		return result;
	}

	public Vertex getStart() {
		return start;

	}

	// find out if we can reach element-tag from this vertex
	private boolean elementProof(Vertex v) {
		boolean f = false;
		if (empty.containsKey(v)) {
			return empty.get(v);
		}
		if (v.type == 2) {
			empty.put(v, true);
			return true;
		}
		if (v.type == 9) {
			empty.put(v, elementProof(defines.get(v.attr)));
			return elementProof(defines.get(v.attr));
		}
		for (Iterator iterator = v.child.iterator(); iterator.hasNext();) {
			f = (f | elementProof((Vertex) iterator.next()));
		}
		empty.put(v, f);
		return f;
	}

	public Vertex getElement(String name) {
		if (elements.containsKey(name)) {
			return elements.get(name);
		} else {
			return null;
		}
	}

	private WayTo makeWay(Vertex To, WayTo way, Vertex ref) {
		WayTo newWay = new WayTo(To);
		newWay.were.add(To);
		if (way != null) {
			newWay.defines.addAll(way.defines);
			newWay.were.addAll(way.were);
		}
		if (ref != null) {
			newWay.defines.add(ref);
		}
		return newWay;
	}

	// auxiliary method
	private WayTo nextVertexAttr(Vertex endV, WayTo w, HashSet<Integer> types) {
		Vertex i = w.at;
		while (true) {
			boolean fl = false;
			if ((i.parent != null) && (i.parent.type != 5)) {
				while (i.next() != null) {
					i = i.next();
					if (!types.contains(i.type)) {
						fl = true;
						break;
					}
				}

			}
			if (fl) {
				return makeWay(i, w, null);
			} else {
				if (i.parent == null) {
					i = w.defines.pop();
				} else {
					i = i.parent;
				}
			}
			if (i == endV) {
				return null;
			}
		}
	}

	// auxiliary method
	private void nextVertexElements(Vertex endV, WayTo w,
			LinkedList<WayTo> queue) {
		Vertex i = w.at;
		while (true) {
			boolean fl = false;
			if (((i.type == 6) || (i.type == 7)) && (!w.were.contains(i))) {
				queue.add(makeWay(i, w, null));
			}
			if ((i.parent != null) && (i.parent.type != 5)) {
				while (i.next() != null) {
					i = i.next();
					if (i.type != 4) {
						fl = true;
						break;
					}
				}

			}
			if (fl) {
				queue.add(makeWay(i, w, null));
				return;
			} else {
				if (i.parent == null) {
					i = w.defines.pop();
				} else {
					i = i.parent;
				}
			}
			if (i == endV) {
				return;
			}

		}

	}

	// find tags which can follow after l (parameter)

	HashSet<String> findNextElements(Vertex v, LinkedList<Vertex> l) {

		LinkedList<WayTo> q = new LinkedList<WayTo>();
		HashSet<String> res = new HashSet<String>();
		if (v.child.size() == 0) {
			return null;
		}
		q.add(makeWay(v.child.getFirst(), null, null));
		for (int i = 0; i <= l.size(); i++) {
			Vertex s;
			if (i == l.size()) {
				s = null;
			} else {
				s = l.get(i);
			}
			LinkedList<WayTo> ways = new LinkedList<WayTo>();
			while (!q.isEmpty()) {
				WayTo w = q.removeFirst();
				if (w.at.type == 1) {
					if (!w.at.child.isEmpty()) {
						q.add(makeWay(w.at.child.getFirst(), w, null));
					} else {
						nextVertexElements(v, w, q);
					}
					continue;

				}
				if (w.at.type == 2) {

					if (s == null) {
						if (w.at.attr != "") {
							res.add(w.at.attr);
						} else {
							res.add("#");
						}
					} else {
						if (w.at.attr.compareTo(s.attr) == 0) {
							ways.add(w);
						}
					}
					continue;
				}
				if ((w.at.type == 3) || (w.at.type == 7)) {
					q.add(makeWay(w.at.child.getFirst(), w, null));
					continue;
				}
				if (w.at.type == 4) {
					nextVertexElements(v, w, q);
					continue;
				}
				if (w.at.type == 5) {
					for (Iterator iterator = w.at.child.iterator(); iterator
							.hasNext();) {
						Vertex d = (Vertex) iterator.next();
						q.add(makeWay(d, w, null));
					}
					continue;
				}
				if ((w.at.type == 6) || (w.at.type == 8)) {
					q.add(makeWay(w.at.child.getFirst(), w, null));
					nextVertexElements(v, w, q);
					continue;
				}
				if (w.at.type == 9) {
					if (w.were.contains(defines.get(w.at.attr))) {
						continue;
					}
					if (notAllowed.contains(defines.get(w.at.attr))) {
						continue;
					}
					if (empty.get(defines.get(w.at.attr))) {
						q.add(makeWay(defines.get(w.at.attr), w, w.at));

					} else {
						nextVertexElements(v, w, q);
					}
					continue;
				}

				if (w.at.type == 10) {
					continue;
				}

			}
			q = new LinkedList<WayTo>();
			for (Iterator iterator = ways.iterator(); iterator.hasNext();) {
				WayTo w = (WayTo) iterator.next();
				w.were = new HashSet<Vertex>();
				nextVertexElements(v, w, q);
			}

		}
		return res;

	}

	// find necessary attributes for the tag
	LinkedList<String> findObligatoryAttributes(Vertex v) {
		WayTo w;
		LinkedList<String> res = new LinkedList<String>();
		HashSet<Integer> nodeal = new HashSet<Integer>();
		nodeal.add(2);
		nodeal.add(6);
		nodeal.add(7);
		nodeal.add(8);
		if (v.child.getFirst() != null) {
			w = new WayTo(v.child.getFirst());
		} else {
			w = null;
		}
		while (w != null) {
			if (w.at.type == 1) {
				if (!w.at.child.isEmpty()) {
					w = makeWay(w.at.child.getFirst(), w, null);
				} else {
					w = nextVertexAttr(v, w, nodeal);
				}
				continue;
			}

			if (w.at.type == 4) {
				res.add(w.at.attr);
				w = nextVertexAttr(v, w, nodeal);
				continue;
			}
			if ((w.at.type == 5) || ((w.at.type == 3))) {
				w = makeWay(w.at.child.getFirst(), w, null);
				continue;
			}
			if (w.at.type == 9) {
				w = makeWay(defines.get(w.at.attr), w, w.at);
				continue;
			}
			w = nextVertexAttr(v, w, nodeal);
		}
		return res;

	}

	// find optional tags, which can follow after attrs(parameter)
	// public HashSet<String> findOptionalAttributes(Vertex v,
	// LinkedList<String> attrs) {
	// LinkedList<WayTo> q = new LinkedList<WayTo>();
	// HashSet<Integer> nodeal = new HashSet<Integer>();
	// HashSet<String> res = new HashSet<String>();
	// nodeal.add(2);
	// nodeal.add(6);
	// nodeal.add(7);
	// q.add(makeWay(v.child.getFirst(), null, null));
	// Iterator it = v.child.iterator();
	// Vertex j = null;
	// if (v.child.size() != 0) {
	// j = (Vertex) it.next();
	// }
	// while ((it.hasNext())
	// && ((j.type == 8) || (j.type == 7) || (j.type == 2) || ((j.type == 6))))
	// {
	// j = (Vertex) it.next();
	// q.add(makeWay(j, null, null));
	// }
	//
	// for (int i = 0; i <= attrs.size(); i++) {
	// String s;
	// if (i != attrs.size()) {
	// s = attrs.get(i);
	// } else {
	// s = null;
	// }
	// LinkedList<WayTo> ways = new LinkedList<WayTo>();
	// while (!q.isEmpty()) {
	//
	// WayTo w = q.removeFirst();
	// if (w == null) {
	// continue;
	// }
	// if (w.at.type == 1) {
	// if (!w.at.child.isEmpty()) {
	// q.add(makeWay(w.at.child.getFirst(), w, null));
	// } else {
	// q.add(nextVertexAttr(v, w, nodeal));
	// }
	// continue;
	// }
	//
	// if (w.at.type == 3) {
	// q.add(makeWay(w.at.child.getFirst(), w, null));
	// continue;
	// }
	//
	// if (w.at.type == 4) {
	// if (s == null) {
	// res.add(w.at.attr);
	// } else {
	// if (w.at.attr.compareTo(s) == 0) {
	// ways.add(w);
	// }
	// }
	// continue;
	// }
	// if (w.at.type == 5) {
	// for (Iterator iterator = w.at.child.iterator(); iterator
	// .hasNext();) {
	// Vertex d = (Vertex) iterator.next();
	// if (!(nodeal.contains(d.type))) {
	// q.add(makeWay(d, w, null));
	// }
	// }
	// continue;
	// }
	//
	// if (w.at.type == 8) {
	// q.add(makeWay(w.at.child.getFirst(), w, null));
	// q.add(nextVertexAttr(v, w, nodeal));
	// continue;
	// }
	//
	// if (w.at.type == 9) {
	// q.add(makeWay(defines.get(w.at.attr), w, w.at));
	// continue;
	// }
	//
	// q.add(nextVertexAttr(v, w, nodeal));
	//
	// }
	// q = new LinkedList<WayTo>();
	// for (Iterator iterator = ways.iterator(); iterator.hasNext();) {
	// WayTo w = (WayTo) iterator.next();
	// q.add(nextVertexAttr(v, w, nodeal));
	//
	// }
	//
	// }
	// return res;
	// }
	private void buildTree(Vertex v, AttrTree r) {

		if (v.type == 4) {
			r.attributes.add(v.attr);
			return;
		}

		if (v.type == 5) {
			HashSet<AttrTree> ch = new HashSet<AttrTree>();
			for (Iterator it = v.child.iterator(); it.hasNext();) {
				Vertex s = (Vertex) it.next();
				if (s.type == 2) {
					continue;
				}
				AttrTree n_ch = new AttrTree();
				buildTree(s, n_ch);
				ch.add(n_ch);
			}
			r.choices.add(ch);
			return;
		}

		if ((v.type == 6) || (v.type == 7)) {
			return;
		}

		if (v.type == 9) {
			buildTree(defines.get(v.attr), r);
			return;
		}

		for (Iterator iterator = v.child.iterator(); iterator.hasNext();) {
			Vertex c = (Vertex) iterator.next();
			if (c.type == 2) {
				continue;
			}
			buildTree(c, r);
		}

	}

	private void reachCompute(AttrTree r) {
		r.canReach.addAll(r.attributes);
		for (Iterator i = r.choices.iterator(); i.hasNext();) {
			HashSet<AttrTree> t = (HashSet<AttrTree>) i.next();
			HashSet<String> rfc = new HashSet<String>();
			for (Iterator j = t.iterator(); j.hasNext();) {
				AttrTree a = (AttrTree) j.next();
				reachCompute(a);
				rfc.addAll(a.canReach);	
			}
			r.reachFromCh.put(t, rfc);
			r.canReach.addAll(rfc);
		}
	}

	private HashSet<String> attrChoosing(AttrTree r, LinkedList<String> toChoose) {
		HashSet<String> res = new HashSet<String>();
		res.addAll(r.attributes);
		LinkedList<String> dp = new LinkedList<String>();
		for (Iterator it = toChoose.iterator(); it.hasNext();) {
			String s = (String) it.next();
			if (r.attributes.contains(s)) {
				dp.add(s);
			}
		}
		toChoose.removeAll(dp);
		if (toChoose.isEmpty()) {
			res.addAll(r.canReach);
			return res;
		}
		for (Iterator i = r.choices.iterator(); i.hasNext();) {
			HashSet<AttrTree> at = (HashSet<AttrTree>) i.next();
			HashSet<String> canreach = r.reachFromCh.get(at);
			LinkedList<String> l = new LinkedList<String>();
 			for (Iterator j = toChoose.iterator(); j.hasNext();) {
				String s = (String) j.next();
				if  (canreach.contains(s)){
					l.add(s);
				}
			}
 			if (l.isEmpty()){
 				res.addAll(canreach);
 				continue;
 			}
 			String s = l.getFirst();
 			for (Iterator j = at.iterator(); j.hasNext();) {
				AttrTree a = (AttrTree) j.next();
				if (a.canReach.contains(s)){
					res.addAll(attrChoosing(a, l));
					break;
				}
			}
 			toChoose.removeAll(l);
		}
		
		return res;
	}

	public HashSet<String> findOptionalAttributes(Vertex v,
			LinkedList<String> attrs) {
		LinkedList<String> s = new LinkedList<String>();
		s.addAll(attrs);
		AttrTree root = new AttrTree();
		buildTree(v, root);
		HashSet<String> res = new HashSet<String>();
		reachCompute(root);
		res = attrChoosing(root, attrs);
		res.removeAll(s);

		return res;

	}

}
