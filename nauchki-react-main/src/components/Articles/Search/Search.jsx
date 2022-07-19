import '../articles.scss';
import { Typography, Autocomplete, TextField, } from '@mui/material';
import parse from 'autosuggest-highlight/parse';
import match from 'autosuggest-highlight/match';
import { useSelector } from 'react-redux';
import { selectAllPosts } from '../../../store/posts/selectors';
import { useNavigate } from 'react-router-dom';

export function Search ({ setSearchingPostTitle, searchingPostTitle}) {

    const navigate = useNavigate();

    const allPosts = useSelector(selectAllPosts);

    const handleSearchingInput = postTitle => setSearchingPostTitle(postTitle);

    const handleSearchingByEnter = event => {
        if (event.key === 'Enter') {
            if (allPosts.find(el => el.title === event.target.value)) {
                setSearchingPostTitle(event.target.value);
                navigate('/articles');
            }
       }
    }

    return (
        <div className="articles__search-wrapper">
            <Typography variant="h4" gutterBottom component="div" align='center'>
                Полезная информация
            </Typography>
            <Autocomplete
                id="highlights-demo"
                size="small"
                options={allPosts}
                getOptionLabel={(option) => option.title}
                renderInput={(params) => (
                    <TextField 
                        {...params}
                        label="Поиск по статьям"
                        value={searchingPostTitle}
                        onSelect={event => handleSearchingInput(event.target.value)}
                        onKeyPress={event => handleSearchingByEnter(event)}
                        margin="normal"
                        sx={{ width: '70%', borderRadius: '50px' }}
                    />
                )}
                renderOption={(props, option, { inputValue }) => {
                    const matches = match(option.title, inputValue);
                    const parts = parse(option.title, matches);

                    return (
                    <li {...props}>
                        <div>
                        {parts.map((part, index) => (
                            <span
                            key={index}
                            style={{
                                fontWeight: part.highlight ? 700 : 400,
                            }}
                            >
                            {part.text}
                            </span>
                        ))}
                        </div>
                    </li>
                    );
                }}
            />
        </div>
    );
}
