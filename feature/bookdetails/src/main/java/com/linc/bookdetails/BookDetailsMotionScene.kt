package com.linc.bookdetails

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.*

const val IMAGE_MOTION_ID: String = "image_id"
const val CONTENT_MOTION_ID: String = "content_id"
const val BUY_BUTTON_MOTION_ID: String = "buy_button_id"

@OptIn(ExperimentalMotionApi::class)
@Composable
fun bookDetailsMotionScene() = MotionScene {
    val image = createRefFor(IMAGE_MOTION_ID)
    val content = createRefFor(CONTENT_MOTION_ID)
    val buyButton = createRefFor(BUY_BUTTON_MOTION_ID)
    defaultTransition(
        from = constraintSet {
            constrain(image) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = parent.top, bottom = content.top)
                width = Dimension.fillToConstraints
                height = Dimension.percent(1f)
            }
            constrain(content) {
                bottom.linkTo(parent.bottom)
                linkTo(start = parent.start, end = parent.end)
                height = Dimension.percent(0.5f)
                width = Dimension.fillToConstraints
            }
            constrain(buyButton) {
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }
        },
        to = constraintSet {
            constrain(image) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = parent.top, bottom = content.top)
                height = Dimension.percent(1f)
                width = Dimension.fillToConstraints
            }
            constrain(content) {
                linkTo(
                    start = parent.start,
                    end = parent.end,
                    top = parent.top,
                    bottom = parent.bottom
                )
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            }
            constrain(buyButton) {
                bottom.linkTo(parent.bottom, 16.dp)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
            }
        }
    ) {
        onSwipe = OnSwipe(
            anchor = content,
            side = SwipeSide.Middle,
            direction = SwipeDirection.Up,
            mode = SwipeMode.Spring(stiffness = 50f),
            onTouchUp = SwipeTouchUp.AutoComplete
        )
        keyAttributes(image) {
            frame(80) {
                alpha = 0.8f
            }
            frame(100) {
                alpha = 0.5f
            }
        }

    }
}