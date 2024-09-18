import argparse
import os
from PIL import Image


def crop_to_content(image):
    # 获取图像的边界框
    bbox = image.getbbox()

    # 裁剪图像
    cropped_image = image.crop(bbox)

    return cropped_image


# 创建命令行参数解析器
parser = argparse.ArgumentParser(description='Crop images to content')
parser.add_argument('filenames', metavar='filename', type=str, nargs='+',
                    help='image file names to process')

# 解析命令行参数
args = parser.parse_args()

# 遍历每个文件名并进行处理
for filename in args.filenames:
    # 检查文件是否存在
    if not os.path.isfile(filename):
        print(f"File not found: {filename}")
        continue

    # 打开图像文件
    image = Image.open(filename)

    # 裁剪图像
    cropped_image = crop_to_content(image)

    # 构建裁剪后图像的文件名
    base_name, ext = os.path.splitext(filename)
    cropped_filename = f"{base_name}_cropped{ext}"

    # 保存裁剪后的图像
    cropped_image.save(cropped_filename)
    print(f"Cropped image saved: {cropped_filename}")
