package de.tu.darmstadt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.link.Link;

import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendController;
import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendController.BackendInitListener;
import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendController.Levelstate;
import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendController.OnLevelChangeListener;
import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendController.OnLevelstateChangeListener;
import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendControllerImpl;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishURL;

public class HomePage extends WebPage implements OnLevelChangeListener, OnLevelstateChangeListener {
	private static final long serialVersionUID = 1L;
	
	private Label selectedLabel;
	private String selectedComponent;
	private BackendController controller;
	private String[] urlParts;

	private Model<String> lifesModel;
	private Model<String> levelModel;
	private Model<String> urlLabelModel;

    public HomePage(final PageParameters parameters) {
		super(parameters);
		
		this.controller = BackendControllerImpl.getInstance();
		this.controller.init(null, new EmptyBackendListener() );
		this.controller.startLevel(1);
		this.controller.nextUrl();
		
		controller.addOnLevelChangeListener(this);
		controller.addOnLevelstateChangeListener(this);
		
		showURL(this.controller.getUrl());
		addButtons();
		addPhishOriginalWebsiteButton();
		
		this.lifesModel = new Model<String>();
		add(new Label("LifesLabel",lifesModel));
		updateLifesLabel();
		
		this.levelModel = new Model<String>();
		add(new Label("LevelLabel",levelModel));
		updateLevelLabel();
		
		this.urlLabelModel = new Model<String>();
		add(new Label("UrlLabel", this.urlLabelModel));
		updateURLLabel();
    }

	private void updateLifesLabel() {
		lifesModel.setObject("Lifes:"+controller.getLifes());
	}

	private void updateLevelLabel() {
		levelModel.setObject("Level:"+controller.getLevel());
	}
	
	private void updateURLLabel(){
		String[] urlParts = controller.getUrl().getParts();
		int numberOfURLParts = urlParts.length;
		String url = "";
		for(int i = 0; i < numberOfURLParts; i++){
			url = url + urlParts[i];
		}
		this.urlLabelModel.setObject(url);
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
//				controller.userClicked(true);
				if (userSelectedCorrectPart){
					selectedLabel.add(correctLabelAttributeModifier());
				} else {
					selectedLabel.add(wrongLabelAttributeModifier());
				}
				
				updateLifesLabel();//should work through listener, not here
	        }
	    };
	    
		Button button2 = new Button("buttonNext") {
			private static final long serialVersionUID = 1L;

			public void onSubmit() {
	            controller.nextUrl();
	            showURL(controller.getUrl());
	            updateURLLabel();
	        }
	    };
	    
	    form.add(button1);
	    form.add(button2);
		add(form);
	}
	
	private void addPhishOriginalWebsiteButton(){
		Form formPhishOriginal = new Form("formPhishOriginal"){
			private static final long serialVersionUID = 1L;

			protected void onSubmit() {
	            info("Form.onSubmit executed");
	        }
		};
		
		Button buttonPhish = new Button("buttonPhish"){
			private static final long serialVersionUID = 1L;
			
			public void onSubmit(){
				if(controller.showProof()){
					//TODO: Display Proof and then go to next question
					//controller.userClicked(true);
				}else{
					controller.nextUrl();
		            showURL(controller.getUrl());
		            updateURLLabel();
				}
			}
		};
		
		Button buttonOriginal = new Button("buttonOriginal"){
			private static final long serialVersionUID = 1L;

			public void onSubmit(){
				controller.nextUrl();
	            showURL(controller.getUrl());
	            updateURLLabel();
	    	}
	    };
	    
	    formPhishOriginal.add(buttonPhish);
	    formPhishOriginal.add(buttonOriginal);
	    add(formPhishOriginal);
		
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

	@Override
	public void onLevelstateChange(Levelstate new_state, int level) {
		System.out.println("onLevelstateChange fired");
		//here we get notified if the level is finished
		
		switch (new_state) {
		case finished:
			controller.startLevel(controller.getLevel()+1);//TODO:check for max level (shouldnt the controller do this)
			break;
		case failed:
			controller.startLevel(controller.getLevel());//TODO:start with introduction
			break;
		default:
			break;
		}
		
//		updateLevelLabel();
		updateLifesLabel();
	}

	@Override
	public void onLevelChange(int new_levelid) {
		System.out.println("onLevelChange fired");
		
		updateLevelLabel();
		updateLifesLabel();
		
	}
	
	
}