# CST238SRS03
Graphics  

Student Name: Nicholas Slaughter 

Issues:  

Notes:  
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
- You will want to implement the derived ctors as described in:  
https://developer.android.com/reference/android/view/View#public-constructors_4
  
