package de.tu.darmstadt;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendController;
import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendController.BackendInitListener;
import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendControllerImpl;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishURL;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	private Label selectedLabel;
	private String selectedComponent;
	private BackendController controller;
	private String[] urlParts;

	private Model<String> lifesModel;
	private Model<String> levelModel;

    public HomePage(final PageParameters parameters) {
		super(parameters);
		
		this.controller = BackendControllerImpl.getInstance();
		this.controller.init(null, new EmptyBackendListener() );
		this.controller.startLevel(1);
		this.controller.nextUrl();
		
		showURL(this.controller.getUrl());
		addButtons();
		
		this.lifesModel = new Model<String>();
		add(new Label("LifesLabel",lifesModel));
		updateLifesLabel();
		
		this.levelModel = new Model<String>();
		add(new Label("LevelLabel",levelModel));
		updateLevelLabel();
    }

	private void updateLifesLabel() {
		lifesModel.setObject("Lifes:"+controller.getLifes());
	}

	private void updateLevelLabel() {
		levelModel.setObject("Level:"+controller.getLevel());
	}

	private String createLifeString() {
		return "Lifes:"+controller.getLifes();
	}

	private void addButtons() {
		Form form = new Form("form1") {
			private static final long serialVersionUID = 1L;

				protected void onSubmit() {
		            info("Form.onSubmit executed");
		        }
	    };
		    
		Button button1 = new Button("buttonOK") {
			private static final long serialVersionUID = 1L;

			public void onSubmit() {
				int componentIndex = Arrays.asList(urlParts).indexOf(selectedComponent);
				boolean userSelectedCorrectPart = controller.partClicked(componentIndex);							
				if (userSelectedCorrectPart){
					selectedLabel.add(correctLabelAttributeModifier());
				} else {
					selectedLabel.add(wrongLabelAttributeModifier());
				}
				
				updateLifesLabel();
	        }
	    };
	    
		Button button2 = new Button("buttonNext") {
			private static final long serialVersionUID = 1L;

			public void onSubmit() {
	            controller.nextUrl();
	            showURL(controller.getUrl());
	            updateLevelLabel();
	        }
	    };
	    
	    form.add(button1);
	    form.add(button2);
		add(form);
	}

	private void showURL(PhishURL url) {
		String[] components = url.getParts();
		this.urlParts = components;

		final RepeatingView urlLabelComponents = new RepeatingView("urlBasedOutOfRepeatingLabels");
		addOrReplace(urlLabelComponents);
		
		final List<Label> allLabels = new ArrayList<Label>();
		
		for (final String aComponent : components){	
			final Label aLabel = new Label(urlLabelComponents.newChildId(), aComponent);			
			aLabel.setOutputMarkupId(true);
			allLabels.add(aLabel);
			urlLabelComponents.add(aLabel);

			
			aLabel.add(new AjaxEventBehavior("click"){
				private static final long serialVersionUID = 1L;

				@Override
				protected void onEvent(AjaxRequestTarget target) {
					unmarkAllLabels(allLabels, target);
					
					selectedLabel = aLabel;
					selectedComponent = aComponent;
					
					aLabel.add(markedLabelAttributeModifier());
					target.add(aLabel);
				}

				private void unmarkAllLabels(final List<Label> allLabels, AjaxRequestTarget target) {
					for (Label labelToResetMarkedStyle : allLabels){
						labelToResetMarkedStyle.add(unmarkedLabelAttributeModifier());
						target.add(labelToResetMarkedStyle);
					}
				}

			
			});
		}
	}

	private AttributeModifier markedLabelAttributeModifier() {
		String styleAttr = "background-color: lightblue;";
		return new AttributeModifier("style", styleAttr);
	}

	private AttributeModifier unmarkedLabelAttributeModifier() {
		String style = "background-color: white;";
		return new AttributeModifier("style", style);
	}
	
	private AttributeModifier correctLabelAttributeModifier() {
		String style = "background-color: lightgreen;";
		return new AttributeModifier("style", style);
	}
	
	private AttributeModifier wrongLabelAttributeModifier() {
		String style = "background-color: red;";
		return new AttributeModifier("style", style);
	}
	
	public class EmptyBackendListener implements BackendInitListener, Serializable{

		private static final long serialVersionUID = 1L;

		@Override
		public void initProgress(int percent) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onInitDone() {
			// TODO Auto-generated method stub
			
		}
		
	}
}

