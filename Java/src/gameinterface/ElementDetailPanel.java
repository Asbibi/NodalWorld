package gameinterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import gamelogic.Element;

/**
* Create an element manager as a tool bar.
* Includes the list fo the different elements,
* buttons to add/remove/rearrange them,
* a viewer to inspect and modify a specific element.
* 
* @see ControlPanel
*/ 
public class ElementDetailPanel<T extends Element> extends JToolBar {
	private ArrayList<T> elements;
	private JList<Element> scrollList;
	private JButton upButton;
	private JButton downButton;
	private JButton removeButton;
	private JButton addButton;
	private JTextField addTextField;
	
	public ElementDetailPanel(String className) {
		super(null, JToolBar.VERTICAL);
		elements = new ArrayList<T>();
		scrollList = new JList<Element>();
		setUpUI(className);
	}
	
	/**
	* Sets up the UI of the element manager
	*/ 
	private void setUpUI(String className) {
		add(new JLabel(className));
		
		add(scrollList);
		
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
		upButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent event) {
            	moveUpCurrentElement();
            }
        });
		downButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent event) {
            	moveDownCurrentElement();
            }
        });
		topButtonsPanel.add(upButton);
		topButtonsPanel.add(downButton);
		topButtonsPanel.add(removeButton);
		add(topButtonsPanel);
		

		JPanel BottomButtonsPanel = new JPanel();
		addTextField = new JTextField(5);
		ImageIcon addIcon = new ImageIcon("res/_System_Add.png");
		addButton = new JButton(addIcon);
		addButton.setPreferredSize(buttonDimension);
		BottomButtonsPanel.add(addTextField);
		BottomButtonsPanel.add(addButton);
		add(BottomButtonsPanel);
	}
	
	// Button callbacks
	/**
	* Callback for the up button, swap the selected element with the one above
	*/ 
	public void moveUpCurrentElement() {
		int selectedIndex = scrollList.getSelectedIndex();
		System.out.println(selectedIndex);
		if (selectedIndex > 0)
			swapElementArray(selectedIndex, selectedIndex -1);
	}
	/**
	* Callback for the down button, swap the selected element with the one below
	*/ 
	public void moveDownCurrentElement() {
		int selectedIndex = scrollList.getSelectedIndex();
		if (selectedIndex < scrollList.getModel().getSize() -1 && selectedIndex != -1)
			swapElementArray(selectedIndex, selectedIndex +1);
	}
	
	
	// Array management
	/**
	* @param the list of elements to store
	*/ 
	public void setElementArray(ArrayList<T> elements) {
		this.elements = elements;
		CopyElementsToJList();
	}
	/**
	* @param the new element to add to the list
	*/ 
	public void addElementArray(T element) {
		elements.add(element);
		CopyElementsToJList();
	}
	/**
	* @param the index of the element to remove to the list
	*/ 
	public void removeElementArray(int elementIndex) {
		elements.remove(elementIndex);
		CopyElementsToJList();
	}
	/**
	* @param the indexes of the elements to swap in the list
	*/ 
	public void swapElementArray(int firstElementIndex, int secondElementIndex) {
		T temp = elements.get(firstElementIndex);
		elements.set(firstElementIndex, elements.get(secondElementIndex));
		elements.set(secondElementIndex, temp);
		CopyElementsToJList();
	}
	/**
	* This methods copies the ArrayList of Element to the JList displaying them.
	* Should be called every time the ArrayList elements is modified. 
	*/
	private void CopyElementsToJList() {
		Element[] elementArray = new Element[elements.size()];
		for (int i = 0; i < elements.size(); i++) {
			elementArray[i] = elements.get(i);
		}
		scrollList.setListData(elementArray);
	}
}
