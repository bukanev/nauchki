import { Container } from "@material-ui/core";
import React from "react";
import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles((theme) => ({
  root: {
    marginTop: "125px",
    width: "440px",
    backgroundColor: "#A7DBD5",
    borderRadius: "20px",
    padding: "20px 37px",
    textAlign: "center",
  },
}));

export const MainContainer = ({ children, ...props }) => {
  const styles = useStyles();

  return (
    <Container
      className={styles.root}
      container="main"
     /*  maxWidth="xs" */
      {...props}
    >
      {children}
    </Container>
  );
};
