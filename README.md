# Personal Mushaf
*Screenshots and descriptions of current working build are available below [here](#screenshots).*
## Purpose
To streamline the process of memorizing the Qur'an by providing tools that make it more:
* engaging
* more sustainable to do consistently than current conventional methods

### How will Personal Mushaf make Qur'an memorization more _______?

#### Engaging

Most conventional methods currently require one to recite a short portion of a verse multiple times while looking, then to test the short term memory of that portion by reciting without looking. This process is rinsed and repeated for the whole amount that will be memorized. You guessed it, creating a method more engaging than that should be trivial. Literally any other way would be more engaging. The challenge lies in matching the effectiveness of this "brute-force" approach. 

To do that, this app will model the philosophy of the program Anki. Anki is widely-used today for it's approach of giving the user daily lessons optimized for long-term memory based on self-feedback. The user can create a deck of flash-cards, then can have full confidence that they will know the cards inside-out in a couple weeks as long as they follow the daily lessons. The aim of this app is to achieve a similar workflow, except with a system that is tailored for memorizing Arabic text verbatim. 

#### Sustainable to do Consistently

Consistency is arguably the most difficult aspect of Qur'an memorization. This is because the backlog of material that one needs to review to keep it memorized grows larger as they memorize a set amount every single day. The main way Personal Mushaf will make memorizing more sustainable to do consistently is by making self-testing a viable option.

<img src="/readme-images/personalmushafreadmegraph.svg"
     style="float: center: 15px;" />
     
Self-testing revision material is heavily discouraged by experts, mainly due to the danger of reinforcing mistakes that are not caught. The only way around this is to record yourself for the whole duration of testing, then listening to the recording. This doubles the time needed to revise versus having a teacher of classmate test you, which is why it is such a rare practice. 

Once the review backlog grows large enough, the challenge of finding a tester willing to sit for the whole duration of the test becomes arguably harder than preparing for the test itself. There are many reasons for testers not being able to dedicate so much time to one student. As mentioned before, the testers are usually teachers (who are themselves students of a more intensive program, or need to give due attention to too many students), or classmates (that are around the same level of proficiency). Most students are either studying part-time, where they have access to testers only for about an hour a day, or they are studying full-time with online schooling, so even classmates are pressed for time. 

It is unfortunate that revision is the first thing that is sacrificed due to all of these time-limiting constraints. Once the strength of one's revision inevitably becomes weak enough, they fall into a vicious cycle of rememorizing revision that has been forgotten, while still not getting in enough general revision. Now if one were able to test themselves, the root cause of this issue is resolved.

##### Self-testing

Due to the fundamental differences in nature between memorization and revision, this app will have a separate mode for each. Self-testing is a crucial component for both of these modes. 

At a high level, the user's voice will be recorded for each verse, but for the user to progress to the next verse, they will need to tell the app how confident they are in reciting that specific verse correctly. In the best case scenario, where the user is able to fluently recite from beginning to end of some arbitrary portion of the Qur'an, they would only need to refer to the recording of the verses which they felt unconfident about. The app will keep track of how many times the user has revised that particular portion, and will recommend that the user listens to the whole recording once in a while to catch mistakes which ocurred in verses which they felt confident about.

However, for the average case, where the user possibly skips a verse, gets confused with a similar verse, or simply doesn't know what comes next, the app will have specific routines to help the user get back on track while giving the minimum amount of hints, while keeping track of the location and nature of the mistake. For example, if the user is stuck, the app will give the option of "peeking" the current verse visually and will flag the specific parts uncovered to the user as weak.

All mistakes caught in self-testing will be reiterated into the memorization mode to correct them, also with specific routines for each distinct type of mistake. For example, if the user mixes up a verse with another similar verse, the app will show a color coded difference between the two verses, and then will have the user memorize all the differences visually.

## Current Progress

Personal Mushaf is a barebones functional Qur'an reader application at the moment. It's structure right now is simply a collection of RecyclerViews that contain contextual "bookmarks" to different locations in the Qur'an. Tapping one of these bookmarks opens up a new activity containing a ViewPager of PNG images that happen to be the pages of the Qur'an at the location of the bookmark.

## Screenshots

<table>
  <tr>
    <td>First Level of Navigation Menu</td>
     <td>Second Level of Navigation Menu</td>
  </tr>
  <tr>
    <td><img src="/readme-images/firstscreen.gif" width=320 height=693></td>
    <td><img src="/readme-images/secondscreen.gif" width=320 height=693></td>
  </tr>
 </table>
 
 <table>
  <tr>
    <td>Main Reading Screen</td>
  </tr>
  <tr>
    <td><img src="/readme-images/pager.gif" width=320 height=693></td>
  </tr>
 </table>
 
 <table>
  <tr>
    <td>Dual Page Functionality</td>
  </tr>
  <tr>
    <td><img src="/readme-images/dualpage.gif" width=693 height=320></td>
  </tr>
 </table>
 
