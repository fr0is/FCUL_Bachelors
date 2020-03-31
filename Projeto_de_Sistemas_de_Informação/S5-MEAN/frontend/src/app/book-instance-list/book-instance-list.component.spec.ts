import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookInstanceListComponent } from './book-instance-list.component';

describe('BookInstanceListComponent', () => {
  let component: BookInstanceListComponent;
  let fixture: ComponentFixture<BookInstanceListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookInstanceListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookInstanceListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
