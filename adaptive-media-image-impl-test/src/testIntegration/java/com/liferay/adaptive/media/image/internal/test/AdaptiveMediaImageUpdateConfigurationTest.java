/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.adaptive.media.image.internal.test;

import com.liferay.adaptive.media.AdaptiveMediaImageConfigurationException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
@Sync
public class AdaptiveMediaImageUpdateConfigurationTest
	extends BaseAdaptiveMediaImageConfigurationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testUpdateDisabledConfigurationEntry() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			configurationHelper.addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		configurationHelper.disableAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), configurationEntry.getUUID());

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertDisabled(configurationEntryOptional);

		configurationHelper.updateAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one-bis", "1-bis",
			configurationEntry.getProperties());

		configurationEntryOptional =
			configurationHelper.getAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1-bis");

		Assert.assertTrue(configurationEntryOptional.isPresent());

		assertDisabled(configurationEntryOptional);

		Assert.assertTrue(configurationEntryOptional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry =
			configurationEntryOptional.get();

		Assert.assertEquals("one-bis", actualConfigurationEntry.getName());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.DuplicateAdaptiveMediaImageConfigurationException.class
	)
	public void testUpdateDuplicateConfiguration() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			configurationHelper.addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		configurationHelper.addAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "2", properties);

		configurationHelper.updateAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "two-bis", "2",
			configurationEntry1.getProperties());
	}

	@Test
	public void testUpdateFirstConfigurationEntryName() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			configurationHelper.addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry2 =
			configurationHelper.addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "2", properties);

		configurationHelper.updateAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", "one-bis", "1",
			configurationEntry1.getProperties());

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry1Optional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualConfigurationEntry1Optional);

		Assert.assertTrue(actualConfigurationEntry1Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry1 =
			actualConfigurationEntry1Optional.get();

		Assert.assertEquals("one-bis", actualConfigurationEntry1.getName());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry2Optional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualConfigurationEntry2Optional);

		Assert.assertTrue(actualConfigurationEntry2Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry2 =
			actualConfigurationEntry2Optional.get();

		Assert.assertEquals(
			configurationEntry2.getName(), actualConfigurationEntry2.getName());

		Map<String, String> actualConfigurationEntry2Properties =
			actualConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));
	}

	@Test
	public void testUpdateFirstConfigurationEntryProperties() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			configurationHelper.addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry2 =
			configurationHelper.addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "2", properties);

		properties = new HashMap<>();

		properties.put("max-height", "500");
		properties.put("max-width", "800");

		configurationHelper.updateAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), configurationEntry1.getUUID(),
			configurationEntry1.getName(), configurationEntry1.getUUID(),
			properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry1Optional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualConfigurationEntry1Optional);

		Assert.assertTrue(actualConfigurationEntry1Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry1 =
			actualConfigurationEntry1Optional.get();

		Assert.assertEquals(
			configurationEntry1.getName(), actualConfigurationEntry1.getName());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"500", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"800", actualConfigurationEntry1Properties.get("max-width"));

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry2Optional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualConfigurationEntry2Optional);

		Assert.assertTrue(actualConfigurationEntry2Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry2 =
			actualConfigurationEntry2Optional.get();

		Assert.assertEquals(
			configurationEntry2.getName(), actualConfigurationEntry2.getName());

		Map<String, String> actualConfigurationEntry2Properties =
			actualConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));
	}

	@Test
	public void testUpdateFirstConfigurationEntryUuid() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			configurationHelper.addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry2 =
			configurationHelper.addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "2", properties);

		configurationHelper.updateAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1", configurationEntry1.getName(),
			"1-bis", configurationEntry1.getProperties());

		Optional<AdaptiveMediaImageConfigurationEntry>
			nonExistantConfigurationEntry1Optional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(nonExistantConfigurationEntry1Optional.isPresent());

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry1Optional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1-bis");

		assertEnabled(actualConfigurationEntry1Optional);

		Assert.assertTrue(actualConfigurationEntry1Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry1 =
			actualConfigurationEntry1Optional.get();

		Assert.assertEquals(
			configurationEntry1.getName(), actualConfigurationEntry1.getName());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry2Optional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualConfigurationEntry2Optional);

		Assert.assertTrue(actualConfigurationEntry2Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry2 =
			actualConfigurationEntry2Optional.get();

		Assert.assertEquals(
			configurationEntry2.getName(), actualConfigurationEntry2.getName());

		Map<String, String> actualConfigurationEntry2Properties =
			actualConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));
	}

	@Test(
		expected = AdaptiveMediaImageConfigurationException.NoSuchAdaptiveMediaImageConfigurationException.class
	)
	public void testUpdateNonExistingConfiguration() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			configurationHelper.addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		configurationHelper.updateAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2", "two", "2",
			configurationEntry1.getProperties());
	}

	@Test
	public void testUpdateSecondConfigurationEntryName() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			configurationHelper.addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry2 =
			configurationHelper.addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "2", properties);

		configurationHelper.updateAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2", "two-bis", "2",
			configurationEntry2.getProperties());

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry2Optional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualConfigurationEntry2Optional);

		Assert.assertTrue(actualConfigurationEntry2Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry2 =
			actualConfigurationEntry2Optional.get();

		Assert.assertEquals("two-bis", actualConfigurationEntry2.getName());

		Map<String, String> actualConfigurationEntry2Properties =
			actualConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry1Optional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualConfigurationEntry1Optional);

		Assert.assertTrue(actualConfigurationEntry1Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry1 =
			actualConfigurationEntry1Optional.get();

		Assert.assertEquals(
			configurationEntry1.getName(), actualConfigurationEntry1.getName());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));
	}

	@Test
	public void testUpdateSecondConfigurationEntryProperties()
		throws Exception {

		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			configurationHelper.addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry2 =
			configurationHelper.addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "2", properties);

		properties = new HashMap<>();

		properties.put("max-height", "500");
		properties.put("max-width", "800");

		configurationHelper.updateAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), configurationEntry2.getUUID(),
			configurationEntry2.getName(), configurationEntry2.getUUID(),
			properties);

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry2Optional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(actualConfigurationEntry2Optional);

		Assert.assertTrue(actualConfigurationEntry2Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry2 =
			actualConfigurationEntry2Optional.get();

		Assert.assertEquals(
			configurationEntry2.getName(), actualConfigurationEntry2.getName());

		Map<String, String> actualConfigurationEntry2Properties =
			actualConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"500", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"800", actualConfigurationEntry2Properties.get("max-width"));

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry1Optional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualConfigurationEntry1Optional);

		Assert.assertTrue(actualConfigurationEntry1Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry1 =
			actualConfigurationEntry1Optional.get();

		Assert.assertEquals(
			configurationEntry1.getName(), actualConfigurationEntry1.getName());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));
	}

	@Test
	public void testUpdateSecondConfigurationEntryUuid() throws Exception {
		AdaptiveMediaImageConfigurationHelper configurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry1 =
			configurationHelper.addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry2 =
			configurationHelper.addAdaptiveMediaImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "two", "2", properties);

		configurationHelper.updateAdaptiveMediaImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2", configurationEntry2.getName(),
			"2-bis", configurationEntry2.getProperties());

		Optional<AdaptiveMediaImageConfigurationEntry>
			nonExistantConfigurationEntry2Optional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(nonExistantConfigurationEntry2Optional.isPresent());

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry2Optional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2-bis");

		assertEnabled(actualConfigurationEntry2Optional);

		Assert.assertTrue(actualConfigurationEntry2Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry2 =
			actualConfigurationEntry2Optional.get();

		Assert.assertEquals(
			configurationEntry2.getName(), actualConfigurationEntry2.getName());

		Map<String, String> actualConfigurationEntry2Properties =
			actualConfigurationEntry2.getProperties();

		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-height"));
		Assert.assertEquals(
			"200", actualConfigurationEntry2Properties.get("max-width"));

		Optional<AdaptiveMediaImageConfigurationEntry>
			actualConfigurationEntry1Optional =
				configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(actualConfigurationEntry1Optional);

		Assert.assertTrue(actualConfigurationEntry1Optional.isPresent());

		AdaptiveMediaImageConfigurationEntry actualConfigurationEntry1 =
			actualConfigurationEntry1Optional.get();

		Assert.assertEquals(
			configurationEntry1.getName(), actualConfigurationEntry1.getName());

		Map<String, String> actualConfigurationEntry1Properties =
			actualConfigurationEntry1.getProperties();

		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-height"));
		Assert.assertEquals(
			"100", actualConfigurationEntry1Properties.get("max-width"));
	}

}