# Android Bangladeshi Currency Detect, Object Detect App using TensorFlow Lite image classification

## Overview

This is an example application for [TensorFlow Lite](https://tensorflow.org/lite)
on Android. It uses
[Image classification](https://www.tensorflow.org/lite/models/image_classification/overview)
to continuously classify whatever it sees from the device's back camera.
Inference is performed using the TensorFlow Lite Java API. The demo app
classifies frames in real-time, displaying the top most probable
classifications. It allows the user to choose between a floating point or
[quantized](https://www.tensorflow.org/lite/performance/post_training_quantization)
model, select the thread count, and decide whether to run on CPU, GPU, or via
[NNAPI](https://developer.android.com/ndk/guides/neuralnetworks).

These instructions walk you through building and
running the demo on an Android device. For an explanation of the source, see
[TensorFlow Lite Android image classification example](https://www.tensorflow.org/lite/models/image_classification/android).

<!-- TODO(b/124116863): Add app screenshot. -->

### Model
Inside Assests folder zip file is there.

Resnet50 
16 batch size
100 epochs
Teachable ML

## Requirements

*   Android Studio 3.2 (installed on a Linux, Mac or Windows machine)

*   Android device in
    [developer mode](https://developer.android.com/studio/debug/dev-options)
    with USB debugging enabled

*   USB cable (to connect Android device to your computer)

![233090843_626919464944441_2065100748483262971_n](https://user-images.githubusercontent.com/58476836/130997939-d66fad6e-97ea-4d8d-94fa-ecfccc12328e.jpg)

![240407189_600856440900181_597519115783163963_n](https://user-images.githubusercontent.com/58476836/130997953-95632914-92f9-4b82-9477-8167ef755798.jpg)


## Assets folder
_Do not delete the assets folder content_. If you explicitly deleted the
files, choose `Build -> Rebuild` to re-download the deleted model files into the
assets folder.
