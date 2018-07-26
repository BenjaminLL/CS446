# GoGrad (Android App)
  ● Main Page:
      
    1. Stores the user’s checklists with default names
    2. Provides the add button that allows adding more checklists or check other academic plans
       
  ● Program Selection Page:
  
    1. Lists four programs, but only the Bachelor of Computer Science is available to check.
    
  ● Option Selection Page:
    
    1. Provides option selections (Such as: BCS without option, BCS/SE, BCS/Bio, BCS/Bus, BCS/DH etc)
    2. Provides academic year selections (From 2013/14 to 18/19)
    3. Does not accept the empty choice (a Toast would show up)
    
  ● Acdemic Plan Page:
    
     1. Shows the acdemic plan based on the program information that the user enter (There are five sections on this view: CS         Units, Math Units, Elective Units, Non-Math Units, and Additional Constraints)
     2. Provides a like button at the bottom right corner:
          a. black-white -> yellow-black: add the acdemic plan to the main page as a checklist
          b. yellow-black -> black-white: delete the acdemic plan from the main
             page as a checklist       
     3. Provides a home button at the toolbar (Go back to the home page)
     4. Allows the user to check the course description by touching each course listed on the page (In this case, descriptions         would only be provided to those specified courses, such as “CS 241”, “CS 1[134]5”, “(Rec: PHY 121)”, “ECE 224 or MTE           325” and other listed requirements in these formats)
     
  ● Checklist Page:

     1. Allows user to mark and edit the courses they have enrolled (User could only type and add under the units with empty           list. For example, user could add their elective courses under elective units and check or uncheck those. Also in             order for local database to update the information, each time after finishing the current and wanting to exit current         page, user have to tap anywhere else to “lose focus”. We are still working on this and hopefully will fix this                 unwilling feature before final demo.)
     2. Provides course descriptions, including prerequisites and corequisites.
     3. Checking the top level of addition constraints would check the lower level of additional constraints automatically.           (vers-vice)
     4. Provides a delete button at the toolbar to delete current plan and all edited data from user would be removed from             databases.
  ● Suggested Course
    
    1. Provides the suggested courses for the next term based on the courses that the user has enrolled. (Satisfying all the          constraints)
    
    
