// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.List;

public final class TagsUtils {
  public static void updateTagsToSearch (List<String> tagsToSearch) {
    UserService userService = UserServiceFactory.getUserService();
    String email = (String) userService.getCurrentUser().getEmail();
 
    // Check to see if user follows any tags
    if (!LoadFollowedTags.hasFollowedTags(email)) {
      System.out.println("follows no tags!");
      return;
    }
    List<FollowedTag> userFollowedTags = LoadFollowedTags.getFollowedTags(email);
    for (FollowedTag tag : userFollowedTags) {
      if (!tagsToSearch.contains(tag.getTag())) {
        System.out.println("adding: " + tag.getTag());
        tagsToSearch.add(tag.getTag());
      }
    } 
  }
}

