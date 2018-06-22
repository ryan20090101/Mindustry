package io.anuke.mindustry.ui.dialogs;

import io.anuke.mindustry.input.DesktopInput;
import io.anuke.mindustry.input.InputHandler;
import io.anuke.mindustry.util.StampUtil;
import io.anuke.ucore.scene.ui.ButtonGroup;
import io.anuke.ucore.scene.ui.ScrollPane;
import io.anuke.ucore.scene.ui.TextButton;
import io.anuke.ucore.scene.ui.layout.Table;

import java.io.IOException;

import static io.anuke.mindustry.Vars.control;
import static io.anuke.mindustry.Vars.stampDirectory;

public class StampChooser extends FloatingDialog {
	public StampChooser(String title) {
		super(title);
		addCloseButton();
		setup();
	}

	public void reload(){
		content().clear();
		setup();
	}

	private void setup(){
		Table stamps = new Table();
		stamps.marginRight(24f).marginLeft(24f);
		ScrollPane pane = new ScrollPane(stamps, "clear");
		pane.setFadeScrollBars(false);

		ButtonGroup<TextButton> group = new ButtonGroup<>();
		InputHandler inHandler = control.input();
		try {for(StampUtil.Stamp stamp : StampUtil.loadStampsFromFolder(stampDirectory)) {
			TextButton button = new TextButton(stamp.name, "toggle");
			button.clicked(() -> {
				if(stamp==null) ((DesktopInput)control.input()).stamp = stamp;
				if(stamp.name == inHandler.stamp.name) return;
				((DesktopInput)control.input()).stamp = stamp;
			});
			stamps.add(button).group(group).update(t -> t.setChecked(inHandler.stamp != null && inHandler.stamp.name.equals(stamp.name)));
		}}catch (IOException e){e.printStackTrace();}
		content().add(pane);
	}
}
