import { useState } from "react";
import { NavLink } from "react-router-dom";
import '../articles.scss';
import {
    List,
    ListItemButton,
    ListItemText,
    Divider,
    Typography,
    CircularProgress
} from '@mui/material';

export function Tags({ tags, isLoading, setCurrentTag, setSearchingPostTitle }) {
    const [selectedIndex, setSelectedIndex] = useState(null);

    const handleListItemClick = (index, tag) => {
        setSelectedIndex(index);
        setCurrentTag(tag);
        setSearchingPostTitle('');
    };

    return (
        <div>
            <NavLink to="/articles">
                <Typography
                    variant="h5"
                    gutterBottom
                    component="div"
                    align='center'
                    onClick={() => handleListItemClick(null, '')}>
                    Темы
                </Typography>
            </NavLink>
            <Divider />
            <List component="nav" aria-label="tags list">
                {isLoading ? (<CircularProgress />) :
                    (
                        tags &&
                        tags.map((tag, index) => (
                            <NavLink to="/articles" className="navlink" key={`${tag}_${index}`}>
                                <ListItemButton
                                    selected={selectedIndex === index}
                                    onClick={() => handleListItemClick(index, { tag })}
                                >
                                    <ListItemText primary={tag} />
                                </ListItemButton>
                            </NavLink>
                        ))
                    )}
            </List>
        </div>
    );
}
