# Importing Image class from PIL module 
from PIL import Image

# Opens a image in RGB mode 
name = r"Icon_Strength"
im = Image.open(name + ".png") 

# Size of the image in pixels (size of original image) 
# (This is not mandatory) 
width, height = im.size 
size = 84
im1 = im.resize((size,size))
im1.save(name+str(size)+".png")

size = 32
im2 = im.resize((size,size))
im2.save(name+"32.png")
