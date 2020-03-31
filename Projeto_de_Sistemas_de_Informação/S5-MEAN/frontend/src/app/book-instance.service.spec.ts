import { TestBed } from '@angular/core/testing';

import { BookInstanceService } from './book-instance.service';

describe('BookInstanceService', () => {
  let service: BookInstanceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BookInstanceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
