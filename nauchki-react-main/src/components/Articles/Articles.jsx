import './articles.scss';
import { Container, Grid, } from '@mui/material';
import Item from '@mui/material/Grid';
import { Search } from './Search/Search';
import { Tags } from './Tags/Tags';
import { Posts as PostsComponent } from './Posts/Posts';
import { OnePost } from './OnePost/OnePost';

export function Articles({
    posts,
    tags,
    isLoading,
    setCurrentTag,
    searchingPost,
    setSearchingPostTitle,
    searchingPostTitle,
    params
}) {

    return (
        <Container xs={'lg'} sx={{ marginBottom: '60px', marginTop: '60px' }}>
            <Search
                setSearchingPostTitle={setSearchingPostTitle}
                searchingPostTitle={searchingPostTitle}
            />
            <Grid container spacing={4}>
                <Grid item xs={2} sx={{ maxWidth: '20%' }}>
                    <Item>
                        <Tags
                            tags={tags}
                            isLoading={isLoading}
                            setCurrentTag={setCurrentTag}
                            setSearchingPostTitle={setSearchingPostTitle}
                        />
                    </Item>
                </Grid>
                <Grid item xs={10}>
                    <Item>
                        {params.id ? <OnePost params={params} /> :
                            <PostsComponent
                                posts={posts}
                                searchingPost={searchingPost}
                            />
                        }
                    </Item>
                </Grid>
            </Grid>
        </Container>
    );
}
