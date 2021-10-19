package gameinterface;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

import gamelogic.GameManager;
import gamelogic.Surface;
import gamelogic.Species;
import gamelogic.Element;

import java.lang.Class;
import java.util.stream.Collectors;

/**
* Create an element manager as a tool bar.<br/><br/>
* 
* Includes the list for the different elements,<br/>
* buttons to add/remove/rearrange them,<br/>
* a viewer to inspect and modify a specific element.
* 
* @see ControlPanel
* @see ElementDetailPanel
* @see Element
*/
public class ElementManagerToolBar<T extends Element> extends JToolBar {
	private GameManager game;
	private Class<T> eltClass;
	private JList<Element> scrollList;
	private JButton upButton;
	private JButton downButton;
	private JButton removeButton;
	private JButton addButton;
	private JTextField addTextField;
	private ElementDetailPanel detailPanel;
	
	/**
	* @param elementClass the class of elements that are gonna be managed, must be T.class
	* @param detailPanel the instance of detail panel used, should be an instance of the T specific derivated class of ElementDetailPanel
	*/ 
	public ElementManagerToolBar(Class<T> eltClass, ElementDetailPanel detailPanel) {
		super(null, JToolBar.VERTICAL);
		this.eltClass = eltClass;
		setUpUI(getElementClassName(), detailPanel);
	}
	
	/**
	* Sets up the UI of the element manager, called by the constructor.
	* @param title the title of the manager, displayed as a JLabel
	* @param detailPanel the instance of detail panel used
	*/ 
	private void setUpUI(String className, ElementDetailPanel detailPanel) {
		add(new JLabel(className));
		
		scrollList = new JList<Element>() {			
			@Override
			public void setSelectedIndex(int index) {
				super.setSelectedIndex(index);
				if (index >= 0 && index < getElements().size())
					detailPanel.setElement(getElement(index));
				else
					detailPanel.setElement(null);
			}
		};
		scrollList.addMouseListener(new MouseAdapter() {
	         public void mouseClicked(MouseEvent me) {
	             if (me.getClickCount() == 1) {
	                JList<Element> target = (JList<Element>) (me.getSource());
	                if (target == null)
	                	return;
	                int index = target.locationToIndex(me.getPoint());
	                if (index >= 0)
	                	detailPanel.setElement(getElement(index));
	             }
	          }
	       });
		add(new JScrollPane(scrollList));
		
		Dimension buttonDimension = new Dimension(25,25);
		JPanel topButtonsPanel = new JPanel();
		ImageIcon upIcon = new ImageIcon("res/_System_UpArrow.png");
		ImageIcon downIcon = new ImageIcon("res/_System_DownArrow.png");
		ImageIcon delIcon = new ImageIcon("res/_System_DeleteCroce.png");
		upButton = new JButton(upIcon);
		downButton = new JButton(downIcon);
		removeButton = new JButton(delIcon);
		upButton.setPreferredSize(buttonDimension);
		downButton.setPreferredSize(buttonDimension);
		removeButton.setPreferredSize(buttonDimension);
		upButton.addActionListener( e -> moveUpCurrentElement() );
		downButton.addActionListener( e -> moveDownCurrentElement() );
		removeButton.addActionListener( e -> removeCurrentElement() );
		topButtonsPanel.add(upButton);
		topButtonsPanel.add(downButton);
		topButtonsPanel.add(removeButton);
		add(topButtonsPanel);
		

		JPanel BottomButtonsPanel = new JPanel();
		addTextField = new JTextField(5);
		ImageIcon addIcon = new ImageIcon("res/_System_Add.png");
		addButton = new JButton(addIcon);
		addButton.addActionListener( e -> addNewElement() );
		addButton.setPreferredSize(buttonDimension);
		BottomButtonsPanel.add(addTextField);
		BottomButtonsPanel.add(addButton);
		add(BottomButtonsPanel);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(GameFrame.getSeparatorColor());
		add(separator);
		addSeparator();
		this.detailPanel = detailPanel;
		this.detailPanel.addApplyListener(new ActionListener()
			{
            @Override
            public void actionPerformed(ActionEvent event) {
            	int selectedIndex = scrollList.getSelectedIndex();
            	if (selectedIndex < 0)
            		return;
            	detailPanel.applyModificationsToElement(getElement(selectedIndex));
            }
        });
		add(this.detailPanel);
	}
	
	/**
	* Connects the gameManager to the element manager.
	* @param gameManager the gameManager to connect the element manager to
	*/ 
	public void setGameManager(GameManager game) {
		this.game = game;
		CopyElementsToJList();
		if (eltClass == Species.class) {
			((SpeciesDetailPanel)detailPanel).replaceMemberButtonActionListener(
			e -> {
				int selectedIndex = scrollList.getSelectedIndex();
				if (selectedIndex == -1)
					return;
				
				Species sp = (Species)getSelectedElement();
				if (sp == null)
					return;
				
				int reply = JOptionPane.showConfirmDialog(null, "Do you really want to delete all entities of "+ getSelectedElement().toString() +" ? It can't be undone.", "Delete", JOptionPane.YES_NO_OPTION);
	            if (reply != JOptionPane.YES_OPTION)
	                return;				
				
				game.exterminateSpeciesMembers(sp);
				((SpeciesDetailPanel)detailPanel).updateVariableElementInfo(sp);
			});
		}
		repaint();
	}
	
	/**
	* Ask the detail panel to update its diplay only properties.
	*/ 
	public void updateDetails() {
		int selectedIndex = scrollList.getSelectedIndex();
		if (selectedIndex > -1) 
			detailPanel.updateVariableElementInfo(getElement(selectedIndex));
	}
	
	
	
	// ========== Button callbacks ==========
	
	/**
	* Callback for the up button : swap the selected element in the JList with the one above
	*/ 
	public void moveUpCurrentElement() {
		int selectedIndex = scrollList.getSelectedIndex();
		if (selectedIndex > 0) {
			swapElementArray(selectedIndex, selectedIndex -1);
			scrollList.setSelectedIndex(selectedIndex - 1);
		}
	}
	/**
	* Callback for the down button : swap the selected element in the JList with the one below
	*/ 
	public void moveDownCurrentElement() {
		int selectedIndex = scrollList.getSelectedIndex();
		if (selectedIndex < scrollList.getModel().getSize() -1 && selectedIndex != -1) {
			swapElementArray(selectedIndex, selectedIndex +1);
			scrollList.setSelectedIndex(selectedIndex + 1);
		}
	}
	/**
	* Callback for the remove button : remove the selected in the JList element of the game's element list
	*/ 
	public void removeCurrentElement() {
		int selectedIndex = scrollList.getSelectedIndex();
		if (selectedIndex != -1) {
			int reply = JOptionPane.showConfirmDialog(null, "Do you really want to delete "+ getSelectedElement().toString() +" ? It can't be undone.", "Delete", JOptionPane.YES_NO_OPTION);
            if (reply != JOptionPane.YES_OPTION)
                return;
			
			removeElementArray(selectedIndex);
			scrollList.setSelectedIndex(Math.max(selectedIndex - 1, 0));
		}
	}
	/**
	* Callback for the add button : create a new element using the addTextField's text as the element's name.<br/>
	* If the name inputed is empty or already used, the method will stops and a feedback will be given to the user (the addTextField's color will be the "wrong" color defined in the ControlPanel).<br/>
	* If the add is successful, the callback will also reset the addTextField's text to an empty string.
	*/ 
	public void addNewElement() {
		String name = addTextField.getText();
		if (name.isEmpty()) {
			addTextField.setBackground(GameFrame.getWrongFieldColor());
			return;
		}
		
		// check if the name has already be given
		for (Element e : getElements()) {
			if(e.toString().equals(name) ) {
				addTextField.setBackground(GameFrame.getWrongFieldColor());
				return;
			}
		}
		
		T newElement = createElement(name);
		if (newElement == null)
			return;
		addElementArray(newElement);
		scrollList.setSelectedIndex(getElements().size() -1);
		addTextField.setText("");
		addTextField.setBackground(GameFrame.getStandardFieldColor());
	}
	
	/**
	* Method to create the new element, should be override on class instanciation using an anonym class in order to use the T constructor.
	*/
	public T createElement(String name) { return null; }
	
	
	
	
	
	// ========== Array management ==========

	/**
	* @param element the element to add to the list
	*/ 
	public void addElementArray(T element) {
		addElement(element);
		CopyElementsToJList();
	}
	/**
	* @param elementIndex the index of the element to remove to the list
	*/ 
	public void removeElementArray(int elementIndex) {
		removeElement(elementIndex);
		CopyElementsToJList();
	}
	/**
	* @param firstElementIndex the index of the first element to swap in the JList
	* @param secondElementIndex the index of the second element to swap in the JList
	*/ 
	public void swapElementArray(int firstElementIndex, int secondElementIndex) {
		swapElements(firstElementIndex, secondElementIndex);
		CopyElementsToJList();
	}
	/**
	* Updates the JList display array on the gameManager's element list. 
	*/
	private void CopyElementsToJList() {
		Element[] elementArray = new Element[getElements().size() - 1];
		int idx = 0;
		for (T elt : getElements()) {
			if (elt.equals(Surface.getEmpty()) || elt.equals(Species.getEmpty()))
				continue;
			elementArray[idx] = elt;
			idx++;
		}
		scrollList.setListData(elementArray);
	}


	
	// ========== Link to game manager method, depending on element class ==========

	/**
	* @return the element class name
	*/
	private String getElementClassName() {
		if(eltClass.equals(Surface.class)) return "Surface";
		else if(eltClass.equals(Species.class)) return "Species";
		return null;
	}

	/**
	* @return the element list, from the gameManager
	*/
	private List<T> getElements() {
		if (game == null) return null;
		if(eltClass.equals(Surface.class)) return game.getSurfaceArray().stream().map(elt -> eltClass.cast(elt)).collect(Collectors.toList());
		else if(eltClass.equals(Species.class)) return game.getSpeciesArray().stream().map(elt -> eltClass.cast(elt)).collect(Collectors.toList());
		return null;
	}

	/**
	* @return the selected element in the JList
	*/
	private T getSelectedElement() {
		int selectedIndex = scrollList.getSelectedIndex();
		if (selectedIndex != -1) 
			return getElement(selectedIndex);
		else
			return null;
	}
	
	/**
	* WARNING -- Jlist don't show the empty element, meaning for a given element : JList's index = GameManager's index -1 
	* @param index the index in the JList of the element wanted
	* @return the element asked
	*/
	private T getElement(int index) { // +1 because we exclude empty from the display so the JList index has an offset
		if (game == null) return null;
		if(eltClass.equals(Surface.class)) return eltClass.cast(game.getSurface(index + 1));
		else if(eltClass.equals(Species.class)) return eltClass.cast(game.getSpecies(index + 1));
		return null;
	}
	
	/**
	* @param element the element to add at the end of the list
	*/
	private void addElement(T elt) {
		if (game == null) return;
		if(eltClass.equals(Surface.class)) game.addSurface((Surface) elt);
		else if(eltClass.equals(Species.class)) game.addSpecies((Species) elt);
	}

	/**
	* WARNING -- Jlist don't show the empty element, meaning for a given element : JList's index = GameManager's index -1 
	* @param index the index in the JList of the element to remove
	*/
	private void removeElement(int index) { // +1 because we exclude empty from the display so the JList index has an offset
		if (game == null) return;
		if(eltClass.equals(Surface.class)) 
			game.removeSurface(index + 1);
		else if(eltClass.equals(Species.class)) 
			game.removeSpecies(index + 1);
	}
	
	/**
	* WARNING -- Jlist don't show the empty element, meaning for a given element : JList's index = GameManager's index -1 
	* @param firstElementIndex the index of the first element to swap in the JList
	* @param secondElementIndex the index of the second element to swap in the JList
	*/
	private void swapElements(int indexFst, int indexSnd) {	// +1 because we exclude empty from the display so the JList index has an offset
		if (game == null) return;
		if(eltClass.equals(Surface.class)) game.swapSurfaces(indexFst +1, indexSnd+1);
		else if(eltClass.equals(Species.class)) game.swapSpecies(indexFst +1, indexSnd+1);
	}

}
