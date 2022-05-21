import moment from 'moment';
import React, { useEffect } from 'react';
import InputMask from 'react-input-mask';
import beforeMaskedValueChange from '../helpers/beforeMaskedValueChange';

const DOB_FORMAT = 'DD.MM.YYYY';
const YYYYMMDD = /^(18|19|20)\d\d[-/](0[1-9]|1[012])[-/](0[1-9]|[12][0-9]|3[01])$/i;
const formatChars = {
  1: '[0-3]',
  2: '[01]',
  3: '[12]',
  9: '[0-9]',
  a: '[A-Za-z]',
  '*': '[A-Za-z0-9]'
};

export const DateOfBirthInput = props => {
  const { value } = props;

  useEffect(() => {
    if (YYYYMMDD.test(value)) {
      const formattedDate = moment(value).format(DOB_FORMAT);
      props.setDateOfBirth(formattedDate);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [value]);

  function handleChange(event) {
    const { value } = event.target;
    props.setDateOfBirth(value);
  }

  return (
      <InputMask
        beforeMaskedValueChange={beforeMaskedValueChange}
        formatChars={formatChars}
        margin="dense"
        mask="19.29.3999"
        maskChar={null}
        id="input-mask"
        name="input-mask"
        onChange={handleChange}
        placeholder="DD.MM.YYYY"
        variant="standard"
        className='personalArea-form__name'
      >
      </InputMask>
  );
};
