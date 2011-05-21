/*
 * Copyright (c) 2011 Socialize Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.socialize.android.ioc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

import android.content.Context;

/**
 * 
 * @author Jason Polites
 *
 */
public class Container {

	private Map<String, Object> beans;
	private Context context;
	private BeanMapping mapping;
	private ContainerBuilder builder;
	
	protected Container(Context context, BeanMapping mapping, ContainerBuilder builder) {
		super();
		beans = new TreeMap<String, Object>();
		this.context = context;
		this.mapping = mapping;
	}

	@SuppressWarnings("unchecked")
	public final <T extends Object> T getBean(String name) {
		Object bean = beans.get(name);
		if(bean == null) {
			BeanRef beanRef = mapping.getBeanRef(name);
			if(!beanRef.isSingleton()) {
				bean = builder.buildBean(this, beanRef);
			}
		}
		return (T) bean;
	}
	
	public final boolean containsBean(String name) {
		return beans.containsKey(name);
	}
	
	protected void putBean(String name, Object bean) {
		beans.put(name, bean);
	}
	
	protected Map<String, Object> getBeans() {
		return beans;
	}
	
	public BeanMapping getMapping() {
		return mapping;
	}

	protected void setMapping(BeanMapping mapping) {
		this.mapping = mapping;
	}

	public void loadFromAsset(String assetpath) throws IOException {
		InputStream in = null;
		
		try {
			in = context.getAssets().open(assetpath);
			loadFromStream(in);
		}
		finally {
			if(in != null) {
				in.close();
			}
		}
	}
	
	public void loadFromClasspath(String classpath) throws IOException {
		InputStream in = null;
		
		try {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(classpath);
			loadFromStream(in);
		}
		finally {
			if(in != null) {
				in.close();
			}
		}
		
	}
	
	public void loadFromStream(InputStream in) throws IOException {
		
	}
}
