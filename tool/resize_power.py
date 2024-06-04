# Importing Image class from PIL module
from PIL import Image
import os

# Opens a image in RGB mode
def mkimg(name, log=True):
    im = Image.open(name + ".png")
    if log:
        print("Open ", name, "...Done")
    # Size of the image in pixels (size of original image)
    # (This is not mandatory)
    width, height = im.size
    size = 84
    im1 = im.resize((size, size))
    im1.save(name+str(size)+".png")

    size = 32
    im2 = im.resize((size, size))
    im2.save(name+"32.png")


for file in os.listdir("."):
    if file.endswith(".png"):
        name = file[0:-4]
        # print(name)
        mkimg(name)
