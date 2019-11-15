package com.podio.user;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;

import com.podio.APIFactoryProvider;
import com.podio.contact.Profile;
import com.podio.contact.ProfileField;
import com.podio.contact.ProfileFieldValues;
import com.podio.contact.ProfileUpdate;

public class UserAPITestIT {

	private UserAPI getAPI() {
		return APIFactoryProvider.getDefault().getAPI(UserAPI.class);
	}

	@Test
	public void getProfile() {
		Profile profile = getAPI().getProfile();
		Assert.assertEquals(profile.getName(), "Christian Holm");
	}

	@Test
	public void getProfileField() {
		List<String> name = getAPI().getProfileField(ProfileField.NAME);
		Assert.assertEquals(name.get(0), "Christian Holm");
	}

	@Test
	public void updateProfileA() {
		ProfileUpdate update = new ProfileUpdate();
		update.setName("Christian L. Holm");

		getAPI().updateProfile(update);
	}

	@Test
	public void updateProfileB() {
		ProfileFieldValues fields = new ProfileFieldValues();
		fields.setValue(ProfileField.NAME, "Christian L. Holm");

		getAPI().updateProfile(fields);
	}

	@Test
	public void updateProfileFieldSingle() {
		getAPI().updateProfileField(ProfileField.NAME, "Christian L. Holm");
	}

	@Test
	public void updateProfileFieldMulti() {
		getAPI().updateProfileField(ProfileField.ADDRESS, "Borgergade 144",
				"1300 Kbh K");
	}

	@Test
	public void updateProfileFieldMultiSingle() {
		getAPI().updateProfileField(ProfileField.ADDRESS, "Borgergade 144");
	}

	@Test
	public void getUser() {
		User user = getAPI().getUser();
		Assert.assertEquals(user.getId(), 1);
	}

	@Test
	public void getUpdateUser() {
		getAPI().updateUser(
				new UserUpdate(new Locale("da", "DK"), TimeZone
						.getTimeZone("Europe/Copenhagen")));
	}

	@Test
	public void getStatus() {
		UserStatus status = getAPI().getStatus();

		User user = status.getUser();
		Assert.assertEquals(user.getId(), 1);
		Assert.assertEquals(user.getMail(), "dev@hoisthq.com");
		Assert.assertEquals(user.getStatus(), UserStatusType.ACTIVE);
		Assert.assertEquals(user.getLocale().getLanguage(), "en_gb");
		Assert.assertEquals(user.getTimezone().getID(), "Europe/Copenhagen");

		Profile profile = status.getProfile();
		Assert.assertEquals(profile.getUserId().intValue(), 1);
		Assert.assertEquals(profile.getName(), "Christian Holm");
		Assert.assertEquals(profile.getImage().getThumbnailLink(), "https://d1izh0afz5ebv1.cloudfront.net/public/9");

		Assert.assertEquals(status.getProperties().size(), 5);
		Assert.assertEquals(
				status.getProperties().get("global.video").get("value"),
				Boolean.TRUE);

		Assert.assertEquals(status.getCalendarCode(),
				"3OAnjyFACrJjTNe2JLnpn9ZqVe5dLWS59aSLzqtvlXZyXUenbRVqvQwtOMOYZk2T");

		Assert.assertEquals(status.getInboxNew(), 59);
		Assert.assertEquals(status.getMessageUnreadCount(), 5);
		Assert.assertEquals(status.getMailbox(), "cholm.fdb27615");
	}

	@Test
	public void getProperty() {
		boolean value = getAPI().getProperty("global.video");
		Assert.assertEquals(value, true);
	}

	@Test
	public void setProperty() {
		getAPI().setProperty("test.prop", true);
	}

	@Test
	public void deleteProperty() {
		getAPI().deleteProperty("global.video");
	}
}
