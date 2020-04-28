package com.podio.stream;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;

import com.podio.APIFactoryProvider;
import com.podio.common.Reference;
import com.podio.common.ReferenceType;
import com.podio.common.Right;

public class StreamAPITestIT {

	private StreamAPI getAPI() {
		return APIFactoryProvider.getDefault().getAPI(StreamAPI.class);
	}

	@Test
	public void getStreamObject() {
		StreamObject object = getAPI().getStreamObject(
				new Reference(ReferenceType.ITEM, 1));

		Assert.assertEquals(object.getType(), ReferenceType.ITEM);
		Assert.assertEquals(object.getId(), 1);
		Assert.assertEquals(object.getLink(),
				"https://podio.com/hoist/api/apps/bugs/items/1");
		Assert.assertEquals(object.getComments().size(), 2);
		Assert.assertEquals(object.getComments().get(0).getId(), 1);
		Assert.assertEquals(object.getFiles().size(), 3);
		Assert.assertEquals(object.getFiles().get(0).getId(), 1);
		Assert.assertEquals(object.getFiles().get(1).getId(), 8);
		Assert.assertEquals(object.getOrganization().getId(), 1);
		Assert.assertEquals(object.getSpace().getId(), 1);
	}

	@Test
	public void getStreamObjectV2() {
		StreamObjectV2 object = getAPI().getStreamObjectV2(
				new Reference(ReferenceType.ITEM, 1));

		Assert.assertEquals(object.getType(), ReferenceType.ITEM);
		Assert.assertEquals(object.getId(), 1);
		Assert.assertEquals(object.getCreatedBy().getId(), 1);
		Assert.assertEquals(object.getCreatedOn(), new DateTime(2010, 8, 2, 16,
				0, 0, 0, DateTimeZone.UTC));
		Assert.assertEquals(object.getCreatedVia().getId(), 1);
		Assert.assertEquals(object.getLastUpdateOn(), new DateTime(2014, 5, 27,
				10, 36, 5, 0, DateTimeZone.UTC));
		Assert.assertEquals(object.getTitle(), "no & yes");
		Assert.assertEquals(object.getRights().size(), 10);
		Assert.assertTrue(object.getRights().contains(Right.ADD_FILE));
		Assert.assertTrue(object.isCommentsAllowed());
		Assert.assertEquals(object.getLink(),
				"https://podio.com/hoist/api/apps/bugs/items/1");
		Assert.assertEquals(object.getComments().size(), 2);
		Assert.assertEquals(object.getComments().get(0).getId(), 1);
		Assert.assertEquals(object.getFiles().size(), 3);
		Assert.assertEquals(object.getFiles().get(0).getId(), 1);
		Assert.assertEquals(object.getFiles().get(1).getId(), 8);
		Assert.assertEquals(object.getData().get("item_id"), 1);
		Assert.assertEquals(object.getOrganization().getId(), 1);
		Assert.assertEquals(object.getSpace().getId(), 1);
		Assert.assertEquals(object.getApp().getId(), 1);
		Assert.assertEquals(object.getActivities().size(), 9);
	}

	@Test
	public void getGlobalStream() {
		List<StreamObject> objects = getAPI().getGlobalStream(3, 2, null,
				new DateTime(2011, 6, 30, 12, 0, 0, 0, DateTimeZone.UTC));

		Assert.assertEquals(objects.size(), 3);
		Assert.assertEquals(objects.get(2).getType(), ReferenceType.ITEM);
		Assert.assertEquals(objects.get(2).getId(), 20);
	}

	@Test
	public void getOrganizationStream() {
		List<StreamObject> objects = getAPI().getOrganizationStream(1, 3, 2,
				null, new DateTime(2011, 6, 30, 12, 0, 0, 0, DateTimeZone.UTC));

		Assert.assertEquals(objects.size(), 3);
		Assert.assertEquals(objects.get(2).getType(), ReferenceType.ITEM);
		Assert.assertEquals(objects.get(2).getId(), 20);
	}

	@Test
	public void getOrganizationStreamV2() {
		List<StreamObjectV2> objects = getAPI().getOrganizationStreamV2(1, 3,
				2, null,
				new DateTime(2010, 10, 4, 12, 0, 0, 0, DateTimeZone.UTC));

		Assert.assertEquals(objects.size(), 3);
	}

	@Test
	public void getSpaceStream() {
		List<StreamObject> objects = getAPI().getSpaceStream(1, 3, 2, null,
				new DateTime(2011, 6, 30, 12, 0, 0, 0, DateTimeZone.UTC));

		Assert.assertEquals(objects.size(), 3);
		Assert.assertEquals(objects.get(0).getType(), ReferenceType.ITEM);
		Assert.assertEquals(objects.get(0).getId(), 24);
	}

	@Test
	public void getSpaceStreamV2() {
		List<StreamObjectV2> objects = getAPI().getSpaceStreamV2(1, 3, 2, null,
				new DateTime(2010, 10, 4, 12, 0, 0, 0, DateTimeZone.UTC));

		Assert.assertEquals(objects.size(), 3);
	}

	@Test
	public void getAppStreamV2() {
		List<StreamObjectV2> objects = getAPI().getAppStream(1, 3, 2, null, null);

		Assert.assertEquals(objects.size(), 3);
	}
}
