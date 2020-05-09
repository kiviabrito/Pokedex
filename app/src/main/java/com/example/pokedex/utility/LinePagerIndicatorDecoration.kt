package com.example.pokedex.utility

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class LinePagerIndicatorDecoration : ItemDecoration() {
  private val colorActive = -0x1
  private val colorInactive = 0x66FFFFFF

  /**
   * Height of the space the indicator takes up at the bottom of the view.
   */
  private val indicatorHeight = (DP * 16).toInt()

  /**
   * Indicator stroke width.
   */
  private val indicatorStrokeWidth = DP * 2

  /**
   * Indicator width.
   */
  private val indicatorItemLength = DP * 16

  /**
   * Padding between indicators.
   */
  private val indicatorItemPadding = DP * 4

  /**
   * Some more natural animation interpolation
   */
  private val interpolator = AccelerateDecelerateInterpolator()
  private val paint = Paint()

  companion object {
    private val DP = Resources.getSystem().displayMetrics.density
  }

  init {
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = indicatorStrokeWidth
    paint.style = Paint.Style.STROKE
    paint.isAntiAlias = true
  }

  override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    super.onDrawOver(c, parent, state)
    val itemCount = parent.adapter!!.itemCount

    // center horizontally, calculate width and subtract half from center
    val totalLength = indicatorItemLength * itemCount
    val paddingBetweenItems = Math.max(0, itemCount - 1) * indicatorItemPadding
    val indicatorTotalWidth = totalLength + paddingBetweenItems
    val indicatorStartX = (parent.width - indicatorTotalWidth) / 2f

    // center vertically in the allotted space
    val indicatorPosY = parent.height - indicatorHeight / 2f
    drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount)


    // find active page (which should be highlighted)
    val layoutManager = parent.layoutManager as LinearLayoutManager?
    val activePosition = layoutManager!!.findFirstVisibleItemPosition()
    if (activePosition == RecyclerView.NO_POSITION) {
      return
    }

    // find offset of active page (if the user is scrolling)
    val activeChild = layoutManager.findViewByPosition(activePosition)
    val left = activeChild!!.left
    val width = activeChild.width

    // on swipe the active item will be positioned from [-width, 0]
    // interpolate offset for smooth animation
    val progress: Float = interpolator.getInterpolation(left * -1 / width.toFloat())
    drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress, itemCount)
  }

  private fun drawInactiveIndicators(
    c: Canvas,
    indicatorStartX: Float,
    indicatorPosY: Float,
    itemCount: Int
  ) {
    paint.color = colorInactive

    // width of item indicator including padding
    val itemWidth = indicatorItemLength + indicatorItemPadding
    var start = indicatorStartX
    for (i in 0 until itemCount) {
      // draw the line for every item
      c.drawLine(start, indicatorPosY, start + indicatorItemLength, indicatorPosY, paint)
      start += itemWidth
    }
  }

  private fun drawHighlights(
    c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
    highlightPosition: Int, progress: Float, itemCount: Int
  ) {
    paint.color = colorActive

    // width of item indicator including padding
    val itemWidth = indicatorItemLength + indicatorItemPadding
    if (progress == 0f) {
      // no swipe, draw a normal indicator
      val highlightStart = indicatorStartX + itemWidth * highlightPosition
      c.drawLine(
        highlightStart, indicatorPosY,
        highlightStart + indicatorItemLength, indicatorPosY, paint
      )
    } else {
      var highlightStart = indicatorStartX + itemWidth * highlightPosition
      // calculate partial highlight
      val partialLength = indicatorItemLength * progress

      // draw the cut off highlight
      c.drawLine(
        highlightStart + partialLength, indicatorPosY,
        highlightStart + indicatorItemLength, indicatorPosY, paint
      )

      // draw the highlight overlapping to the next item as well
      if (highlightPosition < itemCount - 1) {
        highlightStart += itemWidth
        c.drawLine(
          highlightStart, indicatorPosY,
          highlightStart + partialLength, indicatorPosY, paint
        )
      }
    }
  }

}