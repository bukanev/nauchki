import '../articles.scss';
import { Grid } from '@mui/material';
import { PostCard } from '../PostCard/PostCard';
import Pagination from '@mui/material/Pagination';
import Stack from '@mui/material/Stack';
import { Link } from "react-router-dom";

export function Posts({ posts, searchingPost }) {
    return (
        <Grid
            container
            rowSpacing={6}
            columnSpacing={{ xs: 2, sm: 4, md: 6 }}
            className='posts__container'
        >
            {searchingPost && <PostCard key={searchingPost.id} post={searchingPost} />}
            {posts && !searchingPost && posts.map((el, index) => (
                index < 6 && <PostCard key={el.id} post={el} />
            ))}
            {posts.length >= 6 && !searchingPost &&
            <Stack spacing={2} sx={{ margin: '40px auto 0' }}>
                <Pagination count={10} />
            </Stack>}
        </Grid>
    );
}
