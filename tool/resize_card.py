# Importing Image class from PIL module
import os

from PIL import Image


# Opens a image in RGB mode
def mkimg(name, log=True):
    im = Image.open(name + ".png")
    if log:
        print("Open ", name, "...Done")
    # Size of the image in pixels (size of original image)
    # (This is not mandatory)
    width, height = im.size
    size = (500, 380)
    im1 = im.resize(size)
    im1.save(name+"_p.png")

    size = (250, 190)
    im2 = im.resize(size)
    im2.save(name+".png")


for file in os.listdir("."):
    if file.endswith(".png"):
        name = file[0:-4]
        # print(name)
        mkimg(name)
