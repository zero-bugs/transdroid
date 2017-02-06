package de.timroes.axmlrpc.serializer;

import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCRuntimeException;
import de.timroes.axmlrpc.XMLUtil;
import de.timroes.axmlrpc.xmlcreator.XmlElement;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Tim Roes
 */
public class StructSerializer implements Serializer {

	private static final String STRUCT_MEMBER = "member";
	private static final String STRUCT_NAME = "name";
	private static final String STRUCT_VALUE = "value";

	public XmlElement serialize(Object object) {

		XmlElement struct = new XmlElement(SerializerHandler.TYPE_STRUCT);

		try {

			XmlElement entry, name, value;

			// We can safely cast here, this Serializer should only be called when
			// the parameter is a map.
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String,Object>)object;

			for(Map.Entry<String,Object> member : map.entrySet()) {
				entry = new XmlElement(STRUCT_MEMBER);
				name = new XmlElement(STRUCT_NAME);
				value = new XmlElement(STRUCT_VALUE);
				name.setContent(member.getKey());
				value.addChildren(SerializerHandler.getDefault().serialize(member.getValue()));
				entry.addChildren(name);
				entry.addChildren(value);
				struct.addChildren(entry);
			}

		} catch(XMLRPCException ex) {
			throw new XMLRPCRuntimeException(ex);
		}

		return struct;
	}

}