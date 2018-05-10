# CST238SRS03
Graphics  

Student Name:  Shant Ahuja

Issues:  in PixelDrawing2, I tried to have a separate settings page. I couldn't figure this out using the intent
	method. I wanted to include this in my assignment turn in because I think it would have helped the user
	if they were able to have a separate settings page in order to make their drawing space bigger.

Notes:  PixelDrawing is the working project! PixelDrawing2 is just an idea I couldn't follow through with.
----

Purpose:  

- Learn to use graphics in a user interface.  

Create a application that takes the following as field input:  

- Number of iterations. (find a good default)  
- A fraction between 0.0 and 1.0.  (default to 0.5)  
- A color.  (use a default of your choice)   
- A gesture to select points.  
- A gesture to clear the drawing.  

Display a graphic in a user interface design by using a custom ImageView.  

Have the user select a minimum of 3 vertex points in the ImageView by a gesture to start the drawing.  

Draw Algorithm:  

- Once the vertex points are selected, pick a point as a starting location.  

- Randomly pick one of the vertices.  

- Move the user selected fraction [0.0, 1.0] towards the randomly selected vertex.  

- Draw a point of the selected color at the new location.  

- Using the new location as the starting location, loop for the user selected interations.  

Hints:  

- extend AppCompatImageView and implement View.OnClickListener   
- @Override public void onDraw(Canvas canvas)  
- Do as little work as possible in your onDraw() function.  
- See Canvas.drawPoint and documentation.  
- See https://developer.android.com/guide/topics/ui/custom-components.html
- Read https://material.io/guidelines/patterns/gestures.html#gestures-touch-mechanics

