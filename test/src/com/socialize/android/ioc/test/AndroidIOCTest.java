package com.socialize.android.ioc.test;

import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import com.google.android.testing.mocking.AndroidMock;
import com.google.android.testing.mocking.UsesMocks;
import com.socialize.android.ioc.AndroidIOC;
import com.socialize.android.ioc.Container;
import com.socialize.android.ioc.ContainerBuilder;

public class AndroidIOCTest extends AndroidTestCase {

	public void testAndroidIOCInit() throws Exception {
        AndroidIOC ioc = AndroidIOC.getInstance();
		ioc.init(getContext());
	}
	
	public void testAndroidIOCInitWithName() throws Exception {
        AndroidIOC ioc = AndroidIOC.getInstance();
		ioc.init(getContext(), "android-beans.xml");
	}
	
	
	@UsesMocks ({ContainerBuilder.class, Container.class})
	public void testDestroy() throws Exception {
		ContainerBuilder builder = AndroidMock.createMock(ContainerBuilder.class, getContext());
		Container container = AndroidMock.createMock(Container.class);
		final String filename = "foobar";
		
		MockContext mockContext = new MockContext();
		
		AndroidMock.expect(builder.build(mockContext, filename)).andReturn(container);
		container.destroy();
		
		AndroidMock.replay(builder);
		AndroidMock.replay(container);
		
		AndroidIOC ioc = AndroidIOC.getInstance();
		
		ioc.init(mockContext, filename, builder);
		ioc.destroy();
		
		AndroidMock.verify(builder);
		AndroidMock.verify(container);
	}
	
}
