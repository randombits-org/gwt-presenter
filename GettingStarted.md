# Introduction #

This is a short introduction to the API, essentially providing a simple 'Hello World'-style application.

# Details #

The Model-View-Presenter pattern consists of three parts. This API only implements some basic piping for the Presenter part - it doesn't dictate any particular Model or View implementation. However, it does have some simple support for standard GWT widgets, which this example will use.

## Importing ##

We will need to add the jar file to the project's library files, and then add the following into the application's `*.gwt.xml` file:

```
<inherits name='net.customware.gwt.presenter.Presenter' />
```

## The Presenter ##

The first class off the rack is the `HelloWorldPresenter`. Since we're working with GWT widgets, we'll use the `WidgetPresenter` for convenience, but you could base it directly on `Presenter` or `BasePresenter`, depending on what you need.

```
public class HelloWorldPresenter extends WidgetPresenter<HelloWorldPresenter.Display> {
  public interface Display extends WidgetDisplay {
    public HasValue<String> getName();

    public HasClickHandlers getGo();
  }

  public static final Place PLACE = new Place("HelloWorld");

  public HelloWorldPresenter( Display display, EventBus eventBus ) {
    super( display, eventBus );
  }


  @Override
  protected void onBind() {
    // 'display' is a final global field containing the
    // Display passed into the constructor.
    display.getGo().addClickHandler( new ClickHandler() {
      public void onClick( ClickEvent event ) {
        Window.alert( "Hello, " + display.getName().getValue() + "!" );
      }
    } );
  }

  @Override
  protected void onUnbind() {
    // Add unbind functionality here for more complex presenters.
  }

  public void refreshDisplay() {
    // This is called when the presenter should pull the latest data
    // from the server, etc. In this case, there is nothing to do.
  }

  public void revealDisplay() {
    // Nothing to do. This is more useful in UI which may be buried
    // in a tab bar, tree, etc.
  }

  /**
   * Returning a place will allow this presenter to automatically trigger
   * when '#HelloWorld' is passed into the browser URL.
   */
  @Override
  public Place getPlace() {
    return PLACE;
  }

  @Override
  protected void onPlaceRequest( PlaceRequest request ) {
    // Grab the 'name' from the request and put it into the 'name' field.
    // This allows a tag of '#HelloWorld;name=Foo' to populate the name field.
    String name = request.getParameter( "name", null );
    if ( name != null )
      display.getName().setValue( name );
  }
}
```

## The View ##

In this case, the view is a GWT Widget. The main thing is that it has to implement `HelloWorldPresenter.Display`.

```
public class HelloWorldPanel extends Composite implements HelloWorldPresenter.Display {
  private final TextBox name;
  private final Button go;

  public HelloWorldPanel() {
    FlowPanel panel = new FlowPanel();
    initWidget( panel );

    panel.add( new Label( "What is your name?" ) );
    name = new TextBox();
    name.setText( "World" );
    panel.add( name );

    go = new Button( "Go" );
    panel.add( go );
  }

  public HasValue getName() {
    return name;
  }

  public HasClickHandlers getGo() {
    return go;
  }

  /**
   * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
   */
  public Widget asWidget() {
    return this;
  }

  public void startProcessing() {
    // Do nothing for the moment.
  } 

  public void stopProcessing() {
    // Do nothing for the moment.
  } 
}
```

## Putting it all together ##

Pretty simple, really. Now, we just have to hook it all up. In your `EntryPoint`, put something like this:

```
public class HelloWorldEntryPoint implements EntryPoint {
  public void onModuleLoad() {
  
    // Build the default event bus
    EventBus eventBus = new DefaultEventBus();

    // Build the display and presenter
    HelloWorldPanel display = new HelloWorldPanel();
    HelloWorldPresenter presenter = new HelloWorldPresenter( display, eventBus );

    // Bind the presenter to the display.
    presenter.bind();

    RootPanel.get().add( presenter.getDisplay().asWidget() );

    // Trigger any passed-in history tokens.
    PlaceManager placeManager = new PlaceManager( eventBus );
    placeManager.fireCurrentPlace();
  }
}
```

## GIN/Guice ##

This also works great with [Google GIN](http://code.google.com/p/google-gin/), which is a [Google Guice](http://code.google.com/p/google-guice/)-like API for Direct Injection (DI) in GWT. There is a basic `AbstractGinModule` subclass called `AbstractPresenterModule` which provides some helper methods for binding Presentation/Display classes easily. There is still some improvement to be made, such as automatically registering the `PlaceManager` and `DefaultEventBus`, which for the moment you will have to configure yourself.