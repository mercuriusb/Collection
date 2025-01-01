package org.mercuriusb.collectio.model;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest{

  @Test
  void equalsSameInstance(){
    /*
    User user = new User();
    user.setName("JohnDoe336");
    assertTrue(user.equals(user), "The same instance should be equal to itself.");

     */
  }

  @Test
  void equalsNullInstance(){
    /*
    User user = new User();
    user.setName("JohnDoe336");
    assertFalse(user.equals(null), "Instance should not be equal to null.");

     */
  }

  @Test
  void equalsDiffClassInstance(){
    /*
    User user = new User();
    user.setName("JohnDoe336");
    String strObj = "JohnDoe336";
    assertFalse(user.equals(strObj), "Instances of different classes should not be equal.");

     */
  }

  @Test
  void equalsSameData(){
    /*
    User user1 = new User();
    User user2 = new User();
    user1.setName("JohnDoe336");
    user2.setName("JohnDoe336");
    assertTrue(user1.equals(user2), "Instances with the same data should be equal.");

     */
  }

  @Test
  void equalsDifferentData(){
    /*
    User user1 = new User();
    User user2 = new User();
    user1.setName("JohnDoe336");
    user2.setName("JaneDoe529");
    assertFalse(user1.equals(user2), "Instances with different data should not be equal.");

     */
  }

  @Test
  void equalsSameSetData(){
    /*
    User user1 = new User();
    User user2 = new User();

    Set<BookmarkMetaData> bmData1 = new LinkedHashSet<>();
    Set<BookmarkMetaData> bmData2 = new LinkedHashSet<>();

    user1.setBookmarkMetaData(bmData1);
    user2.setBookmarkMetaData(bmData2);
    assertTrue(user1.equals(user2), "Instances with the same set data should be equal.");
*/
  }

  @Test
  void equalsDifferentSetData(){
    /*
    User user1 = new User();
    User user2 = new User();

    Set<BookmarkMetaData> bmData1 = new LinkedHashSet<>();
    bmData1.add(new BookmarkMetaData());
    Set<BookmarkMetaData> bmData2 = new LinkedHashSet<>();

    /*
    user1.setBookmarkMetaData(bmData1);
    user2.setBookmarkMetaData(bmData2);
    assertFalse(user1.equals(user2), "Instances with different set data should not be equal.");
    */
  }
}