import React, { forwardRef } from "react";
import TextField from "@material-ui/core/TextField";
import { withStyles } from "@material-ui/core";

const CssTextField = withStyles({
  root: {
    "& .MuiOutlinedInput-root": {
      backgroundColor: 'white',
      borderRadius: "40px",
      "& fieldset": {
        borderRadius: "40px",
        border: "3px solid #9A9A9A",
        color: "black",
      },
      "&.Mui-focused fieldset": {
        border: "3px solid #D7D7D7"
      },
      "&.Mui-error fieldset": {
        border: "3px solid #C64F4F",
      },
    },
  },
})(TextField);

export const Input = forwardRef((props, ref) => {
  return (
    <CssTextField
      variant="outlined"
      margin="normal"
      inputRef={ref}
      fullWidth
      {...props}
    />
  );
});
