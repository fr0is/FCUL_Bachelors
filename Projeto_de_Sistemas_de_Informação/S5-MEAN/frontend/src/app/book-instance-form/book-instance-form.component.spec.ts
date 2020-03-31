import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookInstanceFormComponent } from './book-instance-form.component';

describe('BookInstanceFormComponent', () => {
  let component: BookInstanceFormComponent;
  let fixture: ComponentFixture<BookInstanceFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookInstanceFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookInstanceFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
