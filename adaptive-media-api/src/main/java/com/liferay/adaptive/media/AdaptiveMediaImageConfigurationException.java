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

package com.liferay.adaptive.media;

/**
 * @author Sergio González
 */
public class AdaptiveMediaImageConfigurationException extends Exception {

	public AdaptiveMediaImageConfigurationException() {
	}

	public AdaptiveMediaImageConfigurationException(String s) {
		super(s);
	}

	public AdaptiveMediaImageConfigurationException(
		String s, Throwable throwable) {

		super(s, throwable);
	}

	public AdaptiveMediaImageConfigurationException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * This exception is raised when a configuration with the same uuid already exists.
	 */
	public static final class
		DuplicateAdaptiveMediaImageConfigurationException
			extends AdaptiveMediaImageConfigurationException {

		public DuplicateAdaptiveMediaImageConfigurationException() {
		}

		public DuplicateAdaptiveMediaImageConfigurationException(String s) {
			super(s);
		}

		public DuplicateAdaptiveMediaImageConfigurationException(
			String s, Throwable throwable) {

			super(s, throwable);
		}

		public DuplicateAdaptiveMediaImageConfigurationException(
			Throwable throwable) {

			super(throwable);
		}

	}

	/**
	 * This exception is raised when the height value is not valid.
	 */
	public static final class InvalidHeightException
		extends AdaptiveMediaImageConfigurationException {

		public InvalidHeightException() {
		}

		public InvalidHeightException(String s) {
			super(s);
		}

		public InvalidHeightException(String s, Throwable throwable) {
			super(s, throwable);
		}

		public InvalidHeightException(Throwable throwable) {
			super(throwable);
		}

	}

	public static final class
		InvalidStateAdaptiveMediaImageConfigurationException
			extends AdaptiveMediaImageConfigurationException {

		public InvalidStateAdaptiveMediaImageConfigurationException() {
		}

		public InvalidStateAdaptiveMediaImageConfigurationException(String s) {
			super(s);
		}

		public InvalidStateAdaptiveMediaImageConfigurationException(
			String s, Throwable throwable) {

			super(s, throwable);
		}

		public InvalidStateAdaptiveMediaImageConfigurationException(
			Throwable throwable) {

			super(throwable);
		}

	}

	/**
	 * This exception is raised when the height value is not valid.
	 */
	public static final class InvalidWidthException
		extends AdaptiveMediaImageConfigurationException {

		public InvalidWidthException() {
		}

		public InvalidWidthException(String s) {
			super(s);
		}

		public InvalidWidthException(String s, Throwable throwable) {
			super(s, throwable);
		}

		public InvalidWidthException(Throwable throwable) {
			super(throwable);
		}

	}

	/**
	 * This exception is raised when a configuration does not exist.
	 *
	 * @review
	 */
	public static final class
		NoSuchAdaptiveMediaImageConfigurationException
			extends AdaptiveMediaImageConfigurationException {

		public NoSuchAdaptiveMediaImageConfigurationException() {
		}

		public NoSuchAdaptiveMediaImageConfigurationException(String s) {
			super(s);
		}

		public NoSuchAdaptiveMediaImageConfigurationException(
			String s, Throwable throwable) {

			super(s, throwable);
		}

		public NoSuchAdaptiveMediaImageConfigurationException(
			Throwable throwable) {

			super(throwable);
		}

	}

}