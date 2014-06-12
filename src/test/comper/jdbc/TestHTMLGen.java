/*******************************************************************************
 * Copyright 2014 Cai Bowen Zhou Liangpeng
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package test.comper.jdbc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		List<String> strLs = new ArrayList<>();
		strLs.add("123");
		map.put("strLs", strLs);
		Writer writer  = new OutputStreamWriter(new FileOutputStream("success2.html"),"UTF-8");  
		template.process(map, writer);
		System.out.println("TestHTMLGen.test()");
	}

}
