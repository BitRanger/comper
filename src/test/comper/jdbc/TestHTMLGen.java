package test.comper.jdbc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TestHTMLGen {

public static class TestClass {
	String var;
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
}
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws IOException, TemplateException {
		Configuration config = new Configuration();
		config.setDirectoryForTemplateLoading(new File("src"));
		config.setDefaultEncoding("utf-8");
		Template template = config.getTemplate("test.html");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", "hahaha");
		TestClass tc = new TestClass();
		tc.setVar("hahaha value inside class");
		Map<String, Object> map2 = new HashMap<>();
		map2.put("obj", tc);
		map.put("map", map2);
		Writer writer  = new OutputStreamWriter(new FileOutputStream("success2.html"),"UTF-8");  
		template.process(map, writer);
		System.out.println("TestHTMLGen.test()");
	}

}
