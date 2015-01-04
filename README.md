TypewriterView - Text typing effect view
==============

[![demo video](http://img.youtube.com/vi/HjTfJB4JIN8/0.jpg)](https://www.youtube.com/watch?v=HjTfJB4JIN8)

# Custom Attributes

The following custom attributes can be specified.
- textSize - The scaled pixel size
- textColor - The color resource identifier
- typingInterval - The typing interval
- startDelay - The delay in milliseconds before starting typing the characters

Please refer usage in [the sample app](https://github.com/JyunjiKondo/TypewriterView/blob/master/sample/src/main/res/layout/activity_main.xml#L16).


# Event Listeners

TypewriterView exposes a custom event called [OnTypeFinished](https://github.com/JyunjiKondo/TypewriterView/blob/master/library/src/main/java/com/github/jyunjikondo/typewriterview/TypewriterView.java#L110) to notify listeners that the typing has finished.

