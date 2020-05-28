import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuartosServicosComponent } from './quartos-servicos.component';

describe('QuartosServicosComponent', () => {
  let component: QuartosServicosComponent;
  let fixture: ComponentFixture<QuartosServicosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuartosServicosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuartosServicosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
