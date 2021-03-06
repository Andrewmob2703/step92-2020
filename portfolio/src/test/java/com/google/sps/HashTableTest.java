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

package com.google.sps.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class HashTableTest {
  @Test
  public void noComments() {
    //Should return an empty hash map.
    BlogHashMap actual = new BlogHashMap();
    int expected = 0;

    Assert.assertEquals(expected, actual.size());
  }

  @Test
  public void oneBlogPostForEachTag() {
    //Return a Hashmap with LinkedList of size one for each tag.
    BlogHashMap actual = new BlogHashMap();
    List<BlogMessage> blogMessages = new ArrayList<>();

    blogMessages.add(
      new BlogMessage(1l, "#general", "I enjoy pop music", "Steven", "", new ArrayList<BlogMessage>(), System.currentTimeMillis(), 0));
    blogMessages.add(
      new BlogMessage(2l, "#wellbeing", "Country music is the best music", "Tayyaba", "", new ArrayList<BlogMessage>(), System.currentTimeMillis(), 0));
    blogMessages.add(
      new BlogMessage(3l, "#music", "Classical music makes you smarter", "Andrew", "", new ArrayList<BlogMessage>(), System.currentTimeMillis(), 0));
    actual.putInMap(blogMessages);

    Collection<String> keys = Arrays.asList("#general", "#wellbeing", "#music");
    for (String key : keys) {
      List<String> keyInList = new ArrayList<>();
      keyInList.add(key);
      Assert.assertEquals(1, actual.getMessages(keyInList, 1).size());
    }
  }

  @Test
  public void multipleBlogPostsForFewTags() {
    /*should return a Hashmap with the appropriate amount of LinkedLists, 
    whose size is equal to the amount of posts per tag.*/
    BlogHashMap actual = new BlogHashMap();
    List<BlogMessage> blogMessages = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      blogMessages.add(
        new BlogMessage(i, "#general", "Test Message", "Steven", "", new ArrayList<BlogMessage>(), System.currentTimeMillis(), 0));
    }
    for (int i = 0; i < 4; i++) {
      blogMessages.add(
        new BlogMessage(i, "#wellbeing", "Test Message", "Tayyaba", "", new ArrayList<BlogMessage>(), System.currentTimeMillis(), 0));
    }
    for (int i = 0; i < 4; i++) {
      blogMessages.add(
        new BlogMessage(i, "#music", "Test Message", "Andrew", "", new ArrayList<BlogMessage>(), System.currentTimeMillis(), 0));
    }
    actual.putInMap(blogMessages);

    Collection<String> keys = Arrays.asList("#general", "#wellbeing", "#music");
    for (String key : keys) {
      List<String> keyInList = new ArrayList<>();
      keyInList.add(key);
      Assert.assertEquals(4, actual.getMessages(keyInList, 4).size());
    }
  }

  @Test
  public void multipleBlogPostsInOrder(){
    //Should return blog posts in descending order by timestamp.
    BlogHashMap actual = new BlogHashMap();
    List<BlogMessage> expected = new ArrayList<>();
    List<BlogMessage> blogMessages = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      long time = System.currentTimeMillis();
      blogMessages.add(
        new BlogMessage(i, "#general", "Test Message", "Steven", "", new ArrayList<BlogMessage>(), time, 0));
      expected.add(
        new BlogMessage(i, "#general", "Test Message", "Steven", "", new ArrayList<BlogMessage>(), time, 0));
    }
    actual.putInMap(blogMessages);

    for (int i = 0; i < expected.size(); i++) {
      List<String> keyInList = new ArrayList<>();
      keyInList.add("#general");
      Assert.assertEquals(
        expected.get(i).getTimestamp(), actual.getMessages(keyInList, 1).pop().getTimestamp());
    }
  }

  @Test
  public void invalidTag(){
    //Tag should not be supported so should return empty hash table.
    BlogHashMap actual = new BlogHashMap();
    List<BlogMessage> blogMessages = new ArrayList<>();
    blogMessages.add(
      new BlogMessage((long) 1, "#Edm", "I listen to Edm while I work.", "Steven", "", new ArrayList<BlogMessage>(), System.currentTimeMillis(), 0));

    Assert.assertEquals(0, actual.size());
  }
}
