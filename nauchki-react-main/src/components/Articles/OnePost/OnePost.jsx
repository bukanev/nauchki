import './openPost.scss';
import '../articles.scss';
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { selectAllPosts } from "../../../store/posts/selectors";
import { Error404 } from "../../../pages/Error 404/Error404";
import { Grid } from '@mui/material';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';

export const OnePost = ({ params }) => {
  
  const allPosts = useSelector(selectAllPosts);

  const filteredPost = allPosts.filter(
    (post) => post.id.toString() === params.id && post
  )[0];

  const navigate = useNavigate();

  const handleBack = () => {
    navigate("/articles");
  };

  if (filteredPost) {
    return (
      <Grid
        container
        rowSpacing={6}
        columnSpacing={{ xs: 2, sm: 4, md: 6 }}
        className='posts__container'
      >
        <button className="onePost-arrowBtn" onClick={handleBack}>
              <svg
                width="53"
                height="54"
                viewBox="0 0 53 54"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M34.3066 46.0913L15.5688 27.0001L34.3066 7.90881L37.4313 11.0903L21.814 27.0001L37.4313 42.9098L34.3066 46.0913Z"
                  fill="#0EAC99"
                />
              </svg>
        </button>
        <Box
          sx={{
            width: '100%',
            textAlign: 'center',
          }}
        >
          <Typography variant="h4" gutterBottom component="div">
            {filteredPost.title}
          </Typography>
          <Typography variant="h6" gutterBottom component="div">
            {filteredPost.subtitle}
          </Typography>
          <Typography variant="caption" display="block" gutterBottom>
            {filteredPost.tag}
          </Typography>
          <img src="https://via.placeholder.com/640x480/333" alt="image" />
          <Typography variant="body1" gutterBottom sx={{ marginTop: '40px' }}>
            {filteredPost.text}
          </Typography>
        </Box>
      </Grid>
    );
  } 
  return <Error404 />
};
