package common.view;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;

import common.text.DurationFormat;

/**
 * Module for testing new widgets etc.
 * Uses a sash in the middle, between two composites.
 * 
 * @author nicz
 *
 */
public class TestingModule extends SashModule {
	
	private SearchBox           searchBox;
	private SuggestionSearchBox suggestSearchBox;
	//private SuggestionCombo     suggestCombo;

	public TestingModule(Composite parent, int leftWidth) {
		super(parent, leftWidth);
	}

	@Override
	protected void loadWidgets() {
		buildSearchboxDemo(cLeft);
		buildSuggestionSearchboxDemo(cLeft);
		buildSuggestionComboDemo(cLeft);
		buildToolbarDemo(cLeft);
		
		buildDragnDropDemo(cRight);	
		buildFormattersDemo(cRight);
	}
	
	@Override
	protected void loadData() {
	}

	private void buildSearchboxDemo(Composite parent) {
		Group group = widgetsFactory.createGroup(parent, "SearchBox demo");
		
		searchBox = new SearchBox(group) {
			@Override
			public void onSearch() {
				MessageBox.info("Searching for " + searchBox.getSearchText());
			}
		};
	}
	
	private void buildSuggestionSearchboxDemo(Composite parent) {
		Group group = widgetsFactory.createGroup(parent, "SuggestionSearchBox demo");
		
		suggestSearchBox = new SuggestionSearchBox(group) {
			@Override
			public void onSearch() {
				MessageBox.info("Searching for " + suggestSearchBox.getSearchText());
			}
		};
		suggestSearchBox.addSuggestion("Foo");
		suggestSearchBox.addSuggestion("Bar");
		suggestSearchBox.addSuggestion("Blam");
	}
	
	private void buildSuggestionComboDemo(Composite parent) {
		Group group = widgetsFactory.createGroup(parent, "SuggestionCombo demo");
		
		SuggestionCombo suggestCombo = new SuggestionCombo(group);
		suggestCombo.addSuggestion("Foo");
		suggestCombo.addSuggestion("Bar");
		suggestCombo.addSuggestion("Blam");
	}

	private void buildToolbarDemo(Composite parent) {
		Group group = widgetsFactory.createGroup(parent, "ToolBar demo");
		
		ToolBar toolBar = widgetsFactory.createToolbar(group, true);
		String[] icons = new String[] {
				"ok", "filesave", "save", "delete", "play", "stop",
				"go-prev", "go-next", "home", "find", "redo", "star"};
		for (int i=0; i<12; i++) {
			final int fi = i;
			widgetsFactory.createToolItem(toolBar, icons[i], 
					"ToolBar item " + icons[i], new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					MessageBox.info("Pushed ToolBar item " + fi);
				}
			});
		}
	}

	private void buildDragnDropDemo(Composite parent) {
		Group gSource = widgetsFactory.createGroup(parent, "Drag-n-Drop source");
		Group gTarget = widgetsFactory.createGroup(parent, "Drag-n-Drop target");
		final Label lblSource = widgetsFactory.createLabel(gSource, "Source: drag me");
		lblSource.setData(42);
		final Label lblTarget = widgetsFactory.createLabel(gTarget, "Target: drop here");
		
		DragSource dsLabel = new DragSource(lblSource, DND.DROP_MOVE);
		Transfer[] types = new Transfer[] {TextTransfer.getInstance()};
		dsLabel.setTransfer(types);
		dsLabel.addDragListener(new DragSourceAdapter() {
			@Override
			public void dragSetData(DragSourceEvent event) {
				// Provide the data of the requested type.
				if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
					event.data = lblSource.getData().toString();
				}
			}
			@Override
			public void dragFinished(DragSourceEvent event) {
				// If a move operation has been performed, update source
				if (event.detail == DND.DROP_MOVE) {
				    lblSource.setText("Dropped !");
				}
			}
		});
		
		DropTarget dtLabel = new DropTarget(lblTarget, DND.DROP_MOVE);
		dtLabel.setTransfer(types);
		dtLabel.addDropListener(new DropTargetAdapter() {
			@Override
			public void drop(DropTargetEvent event) {
				if (TextTransfer.getInstance().isSupportedType(event.currentDataType)) {
					lblTarget.setText( (String) event.data );
				}
			}
		});
	}
	
	private void buildFormattersDemo(Composite parent) {
		Group gFormat = widgetsFactory.createGroup(parent, "Formatters");
		widgetsFactory.createPushButton(gFormat, "Duration", "timer", 
				"Test duration formatter", false, new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				final long duration = (2*3600 + 42*60 + 23)*1000l + 127l;
				final DurationFormat format = new DurationFormat();
				MessageBox.info(format.format(duration));
			}
		});
	}
	

}
