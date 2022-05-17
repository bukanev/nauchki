import React from "react";
import classes from "./OneChild.scss";

const ChildPost = (props) => {
    return (
        <div className={classes.oneChildPha}>{props.number}. {props.post.childText}</div>
    );
};

export default ChildPost;