package org.spbu.publishutil.cache;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.thaiopensource.util.PropertyMap;
import com.thaiopensource.util.PropertyMapBuilder;
import com.thaiopensource.validate.IncorrectSchemaException;
import com.thaiopensource.validate.Schema;
import com.thaiopensource.validate.SchemaReader;
import com.thaiopensource.validate.ValidateProperty;
import com.thaiopensource.validate.Validator;
import com.thaiopensource.validate.auto.AutoSchemaReader;
import com.thaiopensource.xml.sax.ErrorHandlerImpl;
import com.thaiopensource.xml.sax.Jaxp11XMLReaderCreator;
import com.thaiopensource.xml.sax.XMLReaderCreator;

public class SchemaCache {
	private static final SchemaReader SCHEMA_READER = new AutoSchemaReader();
	private static final XMLReaderCreator XML_READER_CREATOR = new Jaxp11XMLReaderCreator();
	private static final PropertyMap READER_PROPERTIES;
	
	static {
		PropertyMapBuilder builder = new PropertyMapBuilder();
		ValidateProperty.XML_READER_CREATOR.put(builder, XML_READER_CREATOR);
		ValidateProperty.ERROR_HANDLER.put(builder, new ErrorHandlerImpl());
		READER_PROPERTIES = builder.toPropertyMap();
	}
	
	private Map<File, Schema> schemaMap = new HashMap<File, Schema>();
	
	public Schema getSchema(File file) throws IOException, SAXException, IncorrectSchemaException {
		Schema schema = schemaMap.get(file);
		if (schema == null) {
			schema = SCHEMA_READER.createSchema(new InputSource(file.toURI().toString()), READER_PROPERTIES);
			schemaMap.put(file, schema);
		}
		return schema;
	}
	
	public Validator getValidator(File schemaFile, ErrorHandler errorHandler) throws IOException, SAXException, IncorrectSchemaException {
		Schema schema = getSchema(schemaFile);
		PropertyMapBuilder builder = new PropertyMapBuilder();
		ValidateProperty.XML_READER_CREATOR.put(builder, XML_READER_CREATOR);
		ValidateProperty.ERROR_HANDLER.put(builder, errorHandler);
		return schema.createValidator(builder.toPropertyMap());
	}
}
