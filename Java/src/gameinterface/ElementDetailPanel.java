package gameinterface;

import java.util.ArrayList;

import javax.swing.*;

import gamelogic.Element;


public class ElementDetailPanel<T extends Element> extends JToolBar {
	private ArrayList<T> elements;
	private JList<Element> scrollList;
	
	public ElementDetailPanel(String className) {
		super(null, JToolBar.VERTICAL);
		elements = new ArrayList<T>();
		scrollList = new JList<Element>();
		setUpUI(className);
	}
	
	private void setUpUI(String className) {
		add(new JLabel(className));
		add(scrollList);
	}
	
	
	
	public void setElementArray(ArrayList<T> elements) {
		this.elements = elements;
		CopyElementsToJList();
	}
	public void addElementArray(T element) {
		elements.add(element);
		CopyElementsToJList();
	}
	public void removeElementArray(int elementIndex) {
		elements.remove(elementIndex);
		CopyElementsToJList();
	}
	public void swapElementArray(int firstElementIndex, int secondElementIndex) {
		T temp = elements.get(firstElementIndex);
		elements.set(firstElementIndex, elements.get(secondElementIndex));
		elements.set(secondElementIndex, temp);
		CopyElementsToJList();
	}
	private void CopyElementsToJList() {
		Element[] elementArray = new Element[elements.size()];
		for (int i = 0; i < elements.size(); i++) {
			elementArray[i] = elements.get(i);
		}
		scrollList.setListData(elementArray);
	}
}
