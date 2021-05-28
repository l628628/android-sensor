# android-sensor# android-sensor

  本项目使用了Android Developer中对陀螺仪传感器的解释示例中采用的手机旋转角度计算方法，实时显示出其角速度（弧度制）。  
  RESET可重置角度的计算，即将其旋转角度置0并重新累计计算。  
  为保证初始数值正确，设置了一个计时器，在应用启动1秒后刷新其读数。
  
# 主要文件
  [MainActivity.java](android-sensor/app/src/main/java/com/example/android_sensor/MainActivity.java);  
  [activity_main.xml](android-sensor/app/src/main/res/layout/activity_main.xml);
  
# 已知的问题
  1、角速度读数无法显示负数，即无法体现方向；  
  2、长时间放置并且不使用RESET键的情况下，角度读数会出现较大偏差。
