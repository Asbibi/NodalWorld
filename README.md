# NodalWorld

Java swing project for the HCI909 - Advanced UI course.
The goal of this project is to create a world simulation game where the user can set the rules with a nodal programming window.

## Execution

In order to launch the program go to the "Java" directory, in which you will find a JAR file : 
it contains a single entry point, therefore you can execute it directly (no external library is needed).

If you want to take a look at the source files, a documentation is here to help you : 
go to the "doc" directory and open the index.html file, it will open the project's javadoc in your browser.

## Description

When starting the application, the template dialog box will open.
You can then select the template you want to start your world with.
If it's your first time using Nodal World, we recommend you to choose the Demo template. And if you want to experiment with the nodes without bothering with importing species and surfaces, you can try the Basic template.
Note that on the bottom left part of the window you have two input fields that you can use with some templates to determine the dimension of the world's terrain.

<img src="ReadmeImages/NodalWorldTemplatesCapture.png" width="400" align="middle">


Once you've selected your template and possibly loaded a file, a window similar to this should pop.

<img src="ReadmeImages/NodalWorldCapture.png" width="800" >

The first element of this window is the menubar, on the top.
The main part of the window is separated between the Control panel on the left and the World panel on the right.

The World panel is a view of the world. It is not interactive, however you can change its appearance using the menubar's buttons.

The Control panel is composed of several utility toolbars on the left, and the nodal editor on the right.
The nodal editor has 4 tabs, one for each kind of rule (terrain, entity generation, entity movement and entity death).
In this section, you can add node by right clicking and selecting a node in the menu that will open. You can select a node by clicking on it. Its properties will then appear in the Info panel that is located on the bottom part of the nodal editor. You can also link different nodes by dragging the mouse from an ouput to the input you want to connect it with. A link can occur only if the input and output have the same type (i.e. have the same colors). A square shaped input indicates that it has to be connected to an output in order to be valid.
Finally, you can connect terminal nodes (located in the "Rules" and "Terrain" categories) to terrain or species slots in order to make them use the terminal node and all its linked nodes. If a terminal node is connected to an invalid node, the graph will be ignored and a red filter will appear over the node editor.
You can zoom and pan the nodal editor by holding left shift or left control while dragging the mouse. Can can also destroy a selected node by pressing the delete key.

The utility toolbar is composed or 4 toolbars.
The first one is the terrain manager. It offers a terrain visualizer and possibility to focus a specific terrain slot. It also offers the ability to add, remove or rearrange terrain slots. It displays and allows the user to edit the dimensions of the terrain and the terrain trigger period (i.e. after how many simulation steps the terrain graph will be re-evaluated to potentially change the terrain).
The second one is the Surface manager. It also to manipulates the surfaces used by the terrain slots by adding, removing or reordering surfaces. It also give the ability to change the surface's color or image by clicking on the corresponding field. It will then open a file chooser dialog box to change the image or a color wheel dialog box to change the color.
The third one is the Species manager. Like the Surface manager, it allows to manipulate the species of the world. However, the properties displayed are different. The button at the right end of the "Count" field allows to instantly kill all the members of the selected species. The species trigger period is, similarly to the terrain trigger period, the simulation step number to pass before evaluating the species rules graph.
Eventually, the fourth one is the Printer. This is where will be printed the values received by the printer nodes (to print a message, the output of the node must be used by the rest of the graph).
