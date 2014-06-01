package com.ruimo.crop

import javafx.scene.paint.Color
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.control.ScrollPane

class Crop extends Application {
  lazy val url = getParameters.getRaw.get(0)
  lazy val image = new Image(url)
  lazy val width = image.getWidth
  lazy val height = image.getHeight
  lazy val pixelReader = image.getPixelReader

  override def start(primaryStage: Stage) {
    inspectImage(image)
      
    val root = new StackPane()
    root.getChildren().add(new ScrollPane(new ImageView(image)))
    val scene = new Scene(root, 600, 400)
    primaryStage.setTitle("Image Read Test")
    primaryStage.setScene(scene)
    primaryStage.show()
  }

  def inspectImage(img: Image) {
    println("width: " + width)
    println("height: " + height)

    println("top bound: " + findTopBound)
    println("left bound: " + findLeftBound)
    println("bottom bound: " + findBottomBound)
    println("right bound: " + findRightBound)
  }

  def blackCountX(x: Int): Int = (0 until height.toInt).foldLeft(0) { (sum, y) =>
    sum + (if (pixelReader.getColor(x, y) == Color.BLACK) 1 else 0)
  }

  def blackCountY(y: Int): Int = (0 until width.toInt).foldLeft(0) { (sum, x) =>
    sum + (if (pixelReader.getColor(x, y) == Color.BLACK) 1 else 0)
  }

  def findBlackBoundFromLeft: Option[Int] = {
    val threshold = (height * 0.8).toInt
    (0 until width.toInt) find { x => blackCountX(x) > threshold }
  }

  def findBlackBoundFromRight: Option[Int] = {
    val threshold = (height * 0.8).toInt
    (width.toInt - 1 to 0 by -1) find { x => blackCountX(x) > threshold }
  }

  def findBlackBoundFromTop: Option[Int] = {
    val threshold = (width * 0.8).toInt
    (0 until height.toInt) find { y => blackCountY(y) > threshold }
  }

  def findBlackBoundFromBottom: Option[Int] = {
    val threshold = (width * 0.8).toInt
    (height.toInt - 1 to 0 by -1) find { y => blackCountY(y) > threshold }
  }

  def findNonBlackBoundFromXToRight(startX: Int): Option[Int] = {
    val threshold = (height * 0.3).toInt
    (startX until width.toInt) find { x => blackCountX(x) < threshold }
  }

  def findNonBlackBoundFromXToLeft(startX: Int): Option[Int] = {
    val threshold = (height * 0.3).toInt
    (startX to 0 by -1) find { x => blackCountX(x) < threshold }
  }

  def findNonBlackBoundFromYToDown(startY: Int): Option[Int] = {
    val threshold = (width * 0.3).toInt
    (startY until height.toInt) find { y => blackCountY(y) < threshold }
  }

  def findNonBlackBoundFromYToUp(startY: Int): Option[Int] = {
    val threshold = (width * 0.3).toInt
    (height.toInt - 1 to 0 by -1) find { y => blackCountY(y) < threshold }
  }

  def findLeftBound: Option[Int] = findBlackBoundFromLeft.flatMap(findNonBlackBoundFromXToRight)
  def findRightBound: Option[Int] = findBlackBoundFromRight.flatMap(findNonBlackBoundFromXToLeft)
  def findTopBound: Option[Int] = findBlackBoundFromTop.flatMap(findNonBlackBoundFromYToDown)
  def findBottomBound: Option[Int] = findBlackBoundFromBottom.flatMap(findNonBlackBoundFromYToUp)
}
