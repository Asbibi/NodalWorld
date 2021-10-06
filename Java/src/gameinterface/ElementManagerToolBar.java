package gameinterface;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

import gamelogic.GameManager;
import gamelogic.Surface;
import gamelogic.Species;
import gamelogic.Element;

import java.lang.Class;
import java.util.stream.Collectors;

/**
* Create an element manager as a tool bar.
* Includes the list for the different elements,
* buttons to add/remove/rearrange them,
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
	
	public ElementManagerToolBar(Class<T> eltClass, GameManager game, ElementDetailPanel detailPanel) {
		super(null, JToolBar.VERTICAL);
		this.eltClass = eltClass;
		this.game = game;
		setUpUI(getElementClassName(), detailPanel);
		CopyElementsToJList();
	}
	
	/**
	* Sets up the UI of the element manager
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
	
	
	
	
	// === Button callbacks ===
	
	/**
	* Callback for the up button : swap the selected element with the one above
	*/ 
	public void moveUpCurrentElement() {
		int selectedIndex = scrollList.getSelectedIndex();
		System.out.println(selectedIndex);
		if (selectedIndex > 0) {
			swapElementArray(selectedIndex, selectedIndex -1);
			scrollList.setSelectedIndex(selectedIndex - 1);
		}
	}
	/**
	* Callback for the down button : swap the selected element with the one below
	*/ 
	public void moveDownCurrentElement() {
		int selectedIndex = scrollList.getSelectedIndex();
		if (selectedIndex < scrollList.getModel().getSize() -1 && selectedIndex != -1) {
			swapElementArray(selectedIndex, selectedIndex +1);
			scrollList.setSelectedIndex(selectedIndex + 1);
		}
	}
	/**
	* Callback for the remove button : remove the selected element of the list
	*/ 
	public void removeCurrentElement() {
		int selectedIndex = scrollList.getSelectedIndex();
		if (selectedIndex != -1) {
			int reply = JOptionPane.showConfirmDialog(null, "Do you really want to delete "+ getElement(selectedIndex).toString() +" ? It can't be undone.", "Delete", JOptionPane.YES_NO_OPTION);
            if (reply != JOptionPane.YES_OPTION)
                return;
			
			removeElementArray(selectedIndex);
			scrollList.setSelectedIndex(Math.max(selectedIndex - 1, 0));
		}
	}
	/**
	* Callback for the add button : create a new element using the addTextField's text as the element's name.
	* If the name inputed is empty or already used, the addTextField will become have the "wrong" color from the ColorPanel and the add won't happen.
	* If the add is succesful the callback will also reset the addTextField's text to an empty string.
	* 
	* @see ControlPanel.getWrongFieldColor
	*/ 
	public void addNewElement() {
		String name = addTextField.getText();
		if (name.isEmpty()) {
			addTextField.setBackground(ControlPanel.getWrongFieldColor());
			return;
		}
		
		// check if the name has already be given
		for (Element e : getElements()) {
			if(e.toString().equals(name) ) {
				addTextField.setBackground(ControlPanel.getWrongFieldColor());
				return;
			}
		}
		
		T newElement = createElement(name);
		if (newElement == null)
			return;
		addElementArray(newElement);
		scrollList.setSelectedIndex(getElements().size() -1);
		addTextField.setText("");
		addTextField.setBackground(ControlPanel.getStandardFieldColor());
	}
	/**
	* Method to create the new element, should be override on class instanciation using an anonym class in order to use the T constructor
	*/
	public T createElement(String name) { return null; }
	
	
	
	
	
	
	// Array management
	/**
	* @param the new element to add to the list
	*/ 
	public void addElementArray(T element) {
		addElement(element);
		CopyElementsToJList();
	}
	/**
	* @param the index of the element to remove to the list
	*/ 
	public void removeElementArray(int elementIndex) {
		removeElement(elementIndex);
		CopyElementsToJList();
	}
	/**
	* @param the indexes of the elements to swap in the list
	*/ 
	public void swapElementArray(int firstElementIndex, int secondElementIndex) {
		swapElements(firstElementIndex, secondElementIndex);
		CopyElementsToJList();
	}
	/**
	* This methods copies the ArrayList of Element to the JList displaying them.
	* Should be called every time the ArrayList elements is modified. 
	*/
	private void CopyElementsToJList() {
		Element[] elementArray = new Element[getElements().size()];
		int idx = 0;
		for (T elt : getElements()) {
			elementArray[idx] = elt;
			idx++;
		}
		scrollList.setListData(elementArray);
	}


	// ========== Link to game manager method, depending on element class ==========

	private String getElementClassName() {
		if(eltClass.equals(Surface.class)) return "Surface";
		else if(eltClass.equals(Species.class)) return "Species";
		return null;
	}

	private List<T> getElements() {
		if(eltClass.equals(Surface.class)) return game.getSurfaceArray().stream().map(elt -> eltClass.cast(elt)).collect(Collectors.toList());
		else if(eltClass.equals(Species.class)) return game.getSpeciesArray().stream().map(elt -> eltClass.cast(elt)).collect(Collectors.toList());
		return null;
	}

	private T getElement(int index) {
		if(eltClass.equals(Surface.class)) return eltClass.cast(game.getSurface(index));
		else if(eltClass.equals(Species.class)) return eltClass.cast(game.getSpecies(index));
		return null;
	}

	private void addElement(T elt) {
		if(eltClass.equals(Surface.class)) game.addSurface((Surface) elt);
		else if(eltClass.equals(Species.class)) game.addSpecies((Species) elt);
	}

	private void removeElement(int index) {
		if(eltClass.equals(Surface.class)) return; // TODO
		else if(eltClass.equals(Species.class)) return; // TODO
	}

	private void swapElements(int indexFst, int indexSnd) {
		if(eltClass.equals(Surface.class)) return; // TODO
		else if(eltClass.equals(Species.class)) return; // TODO
	}

}
