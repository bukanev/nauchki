import '../articles.scss';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { CardActionArea, Grid, } from '@mui/material';
import Item from '@mui/material/Grid';
import { NavLink } from 'react-router-dom';

export function PostCard({ post }) {
    return (
        <Grid item xs={6}>
            <NavLink to={`/articles/${post.id}`}>
                <Item>
                    <Card sx={{ maxWidth: 345 }}>
                        <CardActionArea>
                            <CardMedia
                                component="img"
                                height="140"
                                image="https://via.placeholder.com/310x208/333"
                                alt="article image"
                            />
                            <CardContent>
                                <Typography gutterBottom variant="h5" component="div">
                                    {post.title}
                                </Typography>
                                <div className="post-card__text">
                                    <Typography variant="body2" color="text.secondary">
                                        {post.subtitle}
                                    </Typography>
                                    <Typography variant="body3" color="text.secondary">
                                        {post.tag}
                                    </Typography>
                                </div>
                            </CardContent>
                        </CardActionArea>
                    </Card>
                </Item>
            </NavLink>
        </Grid>
    );
}
