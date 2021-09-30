package gameinterface;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

import gamelogic.Element;

/**
* Create an element manager as a tool bar.
* Includes the list for the different elements,
* buttons to add/remove/rearrange them,
* a viewer to inspect and modify a specific element.
* 
* @see ControlPanel, ElementDetailPanel
*/ 
public class ElementManagerToolBar<T extends Element> extends JToolBar {
	private List<T> elements;
	private JList<Element> scrollList;
	private JButton upButton;
	private JButton downButton;
	private JButton removeButton;
	private JButton addButton;
	private JTextField addTextField;
	private ElementDetailPanel detailPanel;
	
	public ElementManagerToolBar(String className, List<T> elements, ElementDetailPanel detailPanel) {
		super(null, JToolBar.VERTICAL);
		this.elements = elements;
		setUpUI(className, detailPanel);
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
				if (index >= 0 && index < elements.size())
					detailPanel.setElement(elements.get(index));
				else
					detailPanel.setElement(null);
			}
		};
		scrollList.addMouseListener(new MouseAdapter() {
	         public void mouseClicked(MouseEvent me) {
	             if (me.getClickCount() == 1) {
	                JList<Element> target = (JList<Element>)me.getSource();
	                if (target == null)
	                	return;
	                int index = target.locationToIndex(me.getPoint());
	                if (index >= 0)
	                	detailPanel.setElement(elements.get(index));
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
		separator.setForeground(new Color(180,180,180));
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
            	detailPanel.applyModificationsToElement(elements.get(selectedIndex));
            }
        });
		add(this.detailPanel);
	}
	
	
	
	
	// Button callbacks
	/**
	* Callback for the up button, swap the selected element with the one above
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
	* Callback for the down button, swap the selected element with the one below
	*/ 
	public void moveDownCurrentElement() {
		int selectedIndex = scrollList.getSelectedIndex();
		if (selectedIndex < scrollList.getModel().getSize() -1 && selectedIndex != -1) {
			swapElementArray(selectedIndex, selectedIndex +1);
			scrollList.setSelectedIndex(selectedIndex + 1);
		}
	}
	/**
	* Callback for the down button, swap the selected element with the one below
	*/ 
	public void removeCurrentElement() {
		int selectedIndex = scrollList.getSelectedIndex();
		if (selectedIndex != -1) {
			removeElementArray(selectedIndex);
			scrollList.setSelectedIndex(Math.max(selectedIndex - 1, 0));
		}
	}
	/**
	* Callback for the down button, swap the selected element with the one below
	*/ 
	public void addNewElement() {
		String name = addTextField.getText();
		if (name.isEmpty()) {
			addTextField.setBackground(ControlPanel.getWrongFieldColor());
			return;
		}
		
		// check if the name has already be given
		for (Element e : elements) {
			if(e.toString().equals(name) ) {
				addTextField.setBackground(ControlPanel.getWrongFieldColor());
				return;
			}
		}
		
		T newElement = createElement(name);
		if (newElement == null)
			return;
		addElementArray(newElement);
		scrollList.setSelectedIndex(elements.size() -1);
		addTextField.setText("");
		addTextField.setBackground(ControlPanel.getStandardFieldColor());
	}
	/**
	* Method to create the new element, should be override on class instanciation to use the T constructor
	*/
	public T createElement(String name) { return null; }
	
	
	
	
	
	
	// Array management
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
