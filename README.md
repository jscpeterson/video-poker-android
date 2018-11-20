# Video Poker
*The project must include a README.md file, with (at least) the following contents. (Optionally, one or more of these can be in separate Markdown files, reachable via links in README.md.)*

## Aims
*Your aims or motivations for selecting the given topic for your app. That is, why did you choose to develop this particular app, and why is this (at least potentially) a useful or interesting app?* <br />
As a fan of casino video poker games, I've developed an interest in exploring the statistics and mathematics of the game. This is a mobile version of these games, however, this project is intended as more of a simulation of these games than a true "game" as I am offering the user plenty of opportunities to cheat. My stretch goals with this project are to allow for further experimentation with the 

## Current State
*A description of the current state of completion/readiness of your app. This should include a “hit list” of deficiencies: any unimplemented/incomplete elements, and known bugs, that would have to be implemented or corrected for a usable prototype (i.e. one that could be given to a skilled user for testing and feedback), ordered with the most urgent items first.*

## Versions
*A list of Android API versions and hardware (including emulators) on which you’ve tested the submitted version of your app, the minimum Android API required, and any other hardware/software/orientation restrictions that you’re aware of. (This includes restrictions on device language, orientation, etc.)*
Throughout development this project has been tested on:
* a physical Galaxy device on API level 26
* a Nexus device emulator on API level 28
* a Nexus device emulator on API level 24
* a physical device on API level 22
* a physical Amazon Fire device on API level ??

There is a compatibility issue on devices prior to API level 26 in which cards toggled as held are not changing color tint.
Past the launcher screen, the device expects to run strictly in a landscape mode, but will not

## Third-party libraries
*A list of the 3rd-party libraries (i.e. anything beyond the Android standard and support libraries) used by your app.*

## External services
*A list of the external services (including Google services such as Sign In, Calendar, Maps, etc.) consumed by your app.*
The only external services this app consumes are CSV files to read the initial database information from, stored on the device so the user can reset defaults if desired.

## Cosmetic stretch goals
*A list of aesthetic/cosmetic (not functional) improvements that you think would improve your app. This list should be ordered, with those that would give the most improvement (in your opinion) listed first.*
* Timer to show the win accumulated rather than immediately changed. 
* Animation to show cards flip over individually on deal and draw.
* Buttons are currently kept as default only for the enabled/disabled states. I would like better looking buttons with this functionality.
* Option menu text looks bad as black on blue - however changing the default text interferes with the buttons as mentioned.

## Functional stretch goals
*A list of functional stretch goals. These should be sorted either with those that would add the most utility at the top, or with those that would be the simplest to implement at the top.*

## Wireframes and user stories
*Links to the wireframes and user stories for your app.*

## ERD and DDL
*Links to the up-to-date ERD and DDL for your app’s data model.*

## Javadocs
*A link to generated Javadoc HTML pages in your repository (see below).*

## Licenses
*Links to all applicable licenses.*

## Build instructions
*A link to instructions for building the app.*

## Usage instructions
*A link to basic instructions for using the app.*

(Note that the above should be formatted effectively with HTML or Markdown—i.e. using headings, bullet or numbered lists, links, etc. Please review the appearance of this document in GitHub Pages, to ensure that your content appears as intended.)