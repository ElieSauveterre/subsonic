/*
 This file is part of Subsonic.

 Subsonic is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Subsonic is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Subsonic.  If not, see <http://www.gnu.org/licenses/>.

 Copyright 2009 (C) Sindre Mehus
 */
package net.sourceforge.subsonic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sourceforge.subsonic.dao.MediaFileDao;
import net.sourceforge.subsonic.domain.MediaFile;
import net.sourceforge.subsonic.filter.ParameterDecodingFilter;
import net.sourceforge.subsonic.service.MediaFileService;
import net.sourceforge.subsonic.util.StringUtil;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller for updating music file ratings.
 *
 * @author Sindre Mehus
 */
public class SetRatingController extends AbstractController {

    private MediaFileService mediaFileService;

	private MediaFileDao mediaFileDao;
	private Ehcache mediaFileMemoryCache;

	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        String path = request.getParameter("path");
        Integer rating = ServletRequestUtils.getIntParameter(request, "rating");

        MediaFile mediaFile = mediaFileService.getMediaFile(path);
		mediaFileDao.starMediaFile(mediaFile.getId(), rating);
		mediaFile.setRating(rating);
		mediaFileMemoryCache.put(new Element(mediaFile.getFile(), mediaFile));

		String url = "main.view?path" + ParameterDecodingFilter.PARAM_SUFFIX
				+ "=" + StringUtil.utf8HexEncode(path);
        return new ModelAndView(new RedirectView(url));
    }

	public void setMediaFileService(MediaFileService mediaFileService) {
		this.mediaFileService = mediaFileService;
    }

	public void setMediaFileDao(MediaFileDao mediaFileDao) {
		this.mediaFileDao = mediaFileDao;
    }

	public void setMediaFileMemoryCache(Ehcache mediaFileMemoryCache) {
		this.mediaFileMemoryCache = mediaFileMemoryCache;
    }
}
